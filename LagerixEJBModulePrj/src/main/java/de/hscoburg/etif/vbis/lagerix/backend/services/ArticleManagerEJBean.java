/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.services;

import de.hscoburg.etif.vbis.lagerix.backend.dao.ArticleDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.ArticleTypeDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.MovementDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.StorageDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.UserDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.YardDAO;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movements;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import de.hscoburg.etif.vbis.lagerix.backend.util.DTOConverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author zuch1000
 */
@Stateless
public class ArticleManagerEJBean implements ArticleManagerEJBRemoteInterface {

    @EJB
    private YardDAO yardDAO;

    @EJB
    private ArticleDAO articleDAO;

    @EJB
    private MovementDAO movementDAO;

    @EJB
    private ArticleTypeDAO articleTypeDAO;

    @EJB
    private StorageDAO storageDAO;

    @EJB
    private UserDAO userDAO;

    @Resource(name = "mail/Email")
    private Session mailSession;

    @RolesAllowed({"EINKAEUFER", "LAGERARBEITER"})
    public int saveMovementEntry(MovementDTO entry, int yardID) {

        Message msg = new MimeMessage(mailSession);

        Yard yard = yardDAO.findById(Yard.class, yardID);

        Article article = articleDAO.findById(Article.class, entry.getArticleID());

        if (yard == null || article == null) {
            return 1;
        }

        Movement m = new Movement();
        if (entry.isBookedIn()) {
            m.setMovement(Movements.INCORPORATE);
            article.setYard(yard);
        } else {
            m.setMovement(Movements.RELEASE);
            article.setYard(null);

            if (articleTypeDAO.getArticleTypeStock(article.getArticleType()) - 1 < article.getArticleType().getMinimumStock()) {
                try {

                    List<User> einkaeufer = userDAO.findAllByGroupAndStorage(Group.EINKAEUFER, article.getArticleType().getStorage());
                    if (einkaeufer != null && einkaeufer.size() > 0) {
                        msg.setSubject("Lagerbestand unterschritten für " + article.getArticleType().getName());

                        for (User u : einkaeufer) {
                            msg.setRecipient(Message.RecipientType.TO,
                                    new InternetAddress(u.getEmail()));
                        }

                        //msg.setFrom(new InternetAddress("lagerix@gmx.de"));
                        msg.setText("Sehr geehrter Einkäufer,\n\nder Meldebestand für " + article.getArticleType().getName() + " wurde unterschritten.\n\nMit freundlichen Grüßen\n\nIhr Lagerix");

                        Transport.send(msg);
                    }
                } catch (MessagingException me) {
                    me.printStackTrace();
                }
            }
        }
        m.setArticle(article);
        m.setTime(new Date());

        movementDAO.save(m);
        articleDAO.merge(article);

        return 0;

    }

    @RolesAllowed({"EINKAEUFER", "LAGERARBEITER"})
    public List<MovementDTO> getMovementEntriesForArticleType(int articleTypeID) {

        List<Movement> list = articleTypeDAO.getMovementsForArticleTypeId(articleTypeID);

        return DTOConverter.convertMovement(list);
    }

    @RolesAllowed({"EINKAEUFER", "LAGERARBEITER"})
    public ArticleTypeDTO getArticleTypeByID(int articleTypeID) {
        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeID);

        return DTOConverter.convert(at);

    }

    @RolesAllowed({"EINKAEUFER", "LAGERARBEITER"})
    public List<ArticleTypeDTO> searchArticleType(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock) {

        List<ArticleType> a = articleTypeDAO.getArticleTypesBy(articleTypeName, articleTypeDescription, articleTypeMinimumStock);

        return DTOConverter.convertArticleType(a);
    }

    @RolesAllowed({"EINKAEUFER"})
    public int updateMinimumStock(int articleTypeId, int newMinStock) {

        ArticleType a = articleTypeDAO.findById(ArticleType.class, articleTypeId);
        if (a == null) {
            return 1;
        }
        a.setMinimumStock(newMinStock);
        articleTypeDAO.merge(a);

        return 0;
    }

    @RolesAllowed({"EINKAEUFER"})
    public List<ArticleTypeDTO> getAllArticleTypesWithUnderrunMinStock() {

        List<ArticleType> articleTypes = articleTypeDAO.getAllArticleTypes();

        List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>();

        for (ArticleType at : articleTypes) {
            if (articleTypeDAO.getArticleTypeStock(at) < at.getMinimumStock()) {
                result.add(DTOConverter.convert(at));
            }
        }

        return result;
    }

    @RolesAllowed({"LAGERVERWALTER"})
    public ArticleTypeDTO createNewArticleType(String name, String description, int storageId) {

        ArticleType a = new ArticleType();

        Storage s = storageDAO.findById(Storage.class, storageId);

        s.addArticleType(a);

        a.setStorage(s);

        a.setName(name);
        a.setDescription(description);
        a.setMinimumStock(0);
        articleTypeDAO.save(a);
        storageDAO.merge(s);

        return DTOConverter.convert(a);

    }

    @RolesAllowed({"LAGERVERWALTER"})
    public List<ArticleTypeDTO> getAllArticleTypes(int storageID) {

        Storage s = storageDAO.findById(Storage.class, storageID);

        return DTOConverter.convertArticleType(s.getArticleTypes());

    }

    @RolesAllowed({"LAGERVERWALTER"})
    public ArticleDTO createNewArticle(int articleTypeID) {

        Article a = new Article();

        a.setArticleType(articleTypeDAO.findById(ArticleType.class, articleTypeID));
        articleDAO.save(a);

        return DTOConverter.convert(a);

    }

    @RolesAllowed({"LAGERVERWALTER"})
    public void deleteArticleType(int articleTypeid) {

        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeid);

        at.getStorage().getArticleTypes().remove(at);

        articleTypeDAO.remove(at);
    }

    @RolesAllowed({"LAGERVERWALTER"})
    public void updateArticleType(ArticleTypeDTO articleType) {

        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleType.getId());
        at.setDescription(articleType.getDescription());
        //at.setMinimumStock(articleType.getMinimumStock());
        at.setName(articleType.getName());

        articleTypeDAO.merge(at);

    }

    @RolesAllowed({"LAGERVERWALTER"})
    public void deleteArticle(int articleId) {
        articleDAO.remove(articleDAO.findById(Article.class, articleId));
    }

    public List<ArticleDTO> getAllArticleByArticleType(int articleTypeId) {
        return DTOConverter.convertArtice(articleTypeDAO.findById(ArticleType.class, articleTypeId).getArticles());
    }

}
