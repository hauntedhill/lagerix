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
 * Manager class for all operations on an Article, Movements or ArticleType.
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

    /**
     * Method for realising or incorporating of an article
     *
     * @param entry - An DTO with alle information like the articleId and the movement type
     * @param yardID - the Id of the yard where the article should be released or incorporated
     * @return 0 if success, 1 on failure
     */
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
                        msg.setSubject("Lagerbestand unterschritten f�r " + article.getArticleType().getName());

                        for (User u : einkaeufer) {
                            msg.setRecipient(Message.RecipientType.TO,
                                    new InternetAddress(u.getEmail()));
                        }

                        //msg.setFrom(new InternetAddress("lagerix@gmx.de"));
                        msg.setText("Sehr geehrter Eink�ufer,\n\nder Meldebestand f�r " + article.getArticleType().getName() + " wurde unterschritten.\n\nMit freundlichen Gr��en\n\nIhr Lagerix");

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

    /**
     * This methods search for all movement entries of an articleType
     *
     * @param articleTypeID - The articleypeId to search after the articles and movements
     * @return A list with all movements for this articleType
     */
    @RolesAllowed({"EINKAEUFER", "LAGERARBEITER"})
    public List<MovementDTO> getMovementEntriesForArticleType(int articleTypeID) {

        List<Movement> list = articleTypeDAO.getMovementsForArticleTypeId(articleTypeID);

        return DTOConverter.convertMovement(list);
    }

    /**
     * This method search for an article by the ID of it
     *
     * @param articleTypeID - The articleType to search for it
     * @return The article or null if the id doesnt exists
     */
    @RolesAllowed({"EINKAEUFER", "LAGERARBEITER"})
    public ArticleTypeDTO getArticleTypeByID(int articleTypeID) {
        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeID);

        return DTOConverter.convert(at);

    }

    /**
     * This method search an article for the parameters passing. If a parameter is null or empty it will be ignored. The parameter are connected with an logical
     * AND and the article must contains the string in its attribute.
     *
     * @param articleTypeName - The name of the article
     * @param articleTypeDescription - The description of the article
     * @param articleTypeMinimumStock - The minimum stock of an article
     * @return A list with all articles found for the parameter passing.
     */
    @RolesAllowed({"EINKAEUFER", "LAGERARBEITER"})
    public List<ArticleTypeDTO> searchArticleType(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock) {

        List<ArticleType> a = articleTypeDAO.getArticleTypesBy(articleTypeName, articleTypeDescription, articleTypeMinimumStock);

        return DTOConverter.convertArticleType(a);
    }

    /**
     * This method updates the minimum stock of an articleType.
     *
     * @param articleTypeId - The articleType to update the minimum stock
     * @param newMinStock - The minimum stock to set.
     * @return 0 for success, 1 for failure
     */
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

    /**
     * This methods returns all ArticleTypes where the count of all articles for the storage is less than the minimumstock
     *
     * @return A list with all articlesTypes
     */
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

    /**
     * This method creates an articleType with the parameter.
     *
     * @param name - The name of the articleType
     * @param description - The description of the articleType
     * @param storageId - The storageID for the new articleType
     * @return The created articleType with ID
     */
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

    /**
     * This method returns all articleTypes for an storage
     *
     * @param storageID - The storage to search for articleTypes
     * @return A list with all articleTypes of the storage
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public List<ArticleTypeDTO> getAllArticleTypes(int storageID) {

        Storage s = storageDAO.findById(Storage.class, storageID);

        return DTOConverter.convertArticleType(s.getArticleTypes());

    }

    /**
     * This method creates an new article for an given articleType
     *
     * @param articleTypeID - The articleTypeId of the new article
     * @return The created article with ID
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public ArticleDTO createNewArticle(int articleTypeID) {

        Article a = new Article();

        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeID);

        at.addArticle(a);

        articleDAO.save(a);
        articleTypeDAO.merge(at);

        return DTOConverter.convert(a);

    }

    /**
     * This method deletes the articleType with the passed id.
     *
     * @param articleTypeid - The id of the articleType to be delete
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public void deleteArticleType(int articleTypeid) {

        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeid);

        at.getStorage().getArticleTypes().remove(at);

        articleTypeDAO.remove(at);
    }

    /**
     * This method updates an existing articleType
     *
     * @param articleType - The DTO with alle the attributes(name and description) to update the articleType with the ID.
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public void updateArticleType(ArticleTypeDTO articleType) {

        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleType.getId());
        at.setDescription(articleType.getDescription());
        //at.setMinimumStock(articleType.getMinimumStock());
        at.setName(articleType.getName());

        articleTypeDAO.merge(at);

    }

    /**
     * This methods delete an article with the id.
     *
     * @param articleId - The id of the article to be delete
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public void deleteArticle(int articleId) {
        articleDAO.remove(articleDAO.findById(Article.class, articleId));
    }

    /**
     * This method return all article for an articleType
     *
     * @param articleTypeId - The articleTypeId
     * @return A list with all articles for the articleType
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public List<ArticleDTO> getAllArticleByArticleType(int articleTypeId) {
        return DTOConverter.convertArtice(articleTypeDAO.findById(ArticleType.class, articleTypeId).getArticles());
    }

}
