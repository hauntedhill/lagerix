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
import de.hscoburg.etif.vbis.lagerix.backend.dao.YardDAO;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movements;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import de.hscoburg.etif.vbis.lagerix.backend.util.DTOConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author zuch1000
 */
@Stateless
public class ArticleManagerEJBean implements ArticleManagerEJBRemoteInterface{

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
    
    
    public int saveMovementEntry(MovementDTO entry, int yardID) {
        Yard yard = yardDAO.findById(Yard.class, yardID);
        
        
        Article article = articleDAO.findById(Article.class, entry.getArticleID());
        
        
        if(yard == null || article == null)
        {
            return 1;
        }
        
        Movement m = new Movement();
        if(entry.isBookedIn())
        {
            m.setMovement(Movements.INCORPORATE);
            article.setYard(yard);
        }
        else
        {
            m.setMovement(Movements.RELEASE);
            article.setYard(null);
        }
        m.setArticle(article);
        m.setTime(new Date());
        
        
        movementDAO.save(m);
        articleDAO.merge(article);
        
        return 0;
        
    }

    public List<MovementDTO> getMovementEntriesForArticleType(int articleTypeID) {
        
        
        List<Movement> list = articleTypeDAO.getMovementsForArticleTypeId(articleTypeID);
        
 
        return DTOConverter.convertMovement(list);
    }

    public ArticleTypeDTO getArticleTypeByID(int articleTypeID) {
        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeID);
        
        
       
        return DTOConverter.convert(at);
        
        
    }

   public List<ArticleTypeDTO> searchArticleType(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock)
   {
       
       List<ArticleType> a = articleTypeDAO.getArticleTypesBy(articleTypeName, articleTypeDescription, articleTypeMinimumStock);
       
       
        
        
        return DTOConverter.convertArticleType(a);
   }

    

    public int updateMinimumStock(int articleTypeId, int newMinStock) {
        
        ArticleType a = articleTypeDAO.findById(ArticleType.class, articleTypeId);
        if(a==null)
        {
            return 1;
        }
        a.setMinimumStock(newMinStock);
        articleTypeDAO.merge(a);
        
        return 0;
    }

    public List<ArticleTypeDTO> getAllArticleTypesWithUnderrunMinStock() {
        
        List<ArticleType> articleTypes = articleTypeDAO.getAllArticleTypes();
        
        List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>();
        
        for(ArticleType at : articleTypes)
        {
           if(articleTypeDAO.getArticleTypeStock(at)<at.getMinimumStock())
           {
            result.add(DTOConverter.convert(at));
           }
        }
        
        return result;
    }

    public ArticleTypeDTO createNewArticleType(String name, String description, int storageId) {
        
        ArticleType a = new ArticleType();
        
        a.setName(name);
        a.setDescription(description);
        a.setMinimumStock(0);
        articleTypeDAO.save(a);
        
        
 
        
        return DTOConverter.convert(a);
        
        
    }

    public List<ArticleTypeDTO> getAllArticleTypes(int storageID) {
        
        
        Storage s = storageDAO.findById(Storage.class, storageID);
        
       
        
        
        return DTOConverter.convertArticleType(s.getArticleTypes());
        
    }

    public ArticleDTO createNewArticle(int articleTypeID) {
        
        
        Article a = new Article();
        
        a.setArticleType(articleTypeDAO.findById(ArticleType.class, articleTypeID));
        articleDAO.save(a);

        return DTOConverter.convert(a);
        
        
    }

    

    
    
}
