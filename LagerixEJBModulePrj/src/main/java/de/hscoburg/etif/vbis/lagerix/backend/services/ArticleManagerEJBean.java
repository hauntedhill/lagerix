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
    
    
    public int saveMovementEntry(MovementDTO entry) {
        Yard yard = yardDAO.findById(Yard.class, entry.getYardID());
        
        
        Article article = articleDAO.findById(Article.class, entry.getArticleID());
        
        
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
        
        
        
        List<MovementDTO> entries = new ArrayList<MovementDTO>();
        
        
        for(Movement m : list)
        {
           MovementDTO dto = new MovementDTO();
           
           dto.setArticleID(m.getArticle().getId());
           dto.setTimestamp(m.getTime().getTime());
           entries.add(dto);
        }
        
        
        return entries;
    }

    public ArticleTypeDTO getArticleTypeByID(int articleTypeID) {
        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeID);
        
        
        ArticleTypeDTO dto = new ArticleTypeDTO();
        
        dto.setId(at.getId());
        dto.setDescription(at.getDescription());
        dto.setName(at.getName());
        dto.setStorageID(at.getStorage().getId());
        dto.setMinimumStock(at.getMinimumStock());
        return dto;
        
        
    }

   public List<ArticleTypeDTO> searchArticleType(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock)
   {
       
       List<ArticleType> a = articleTypeDAO.getArticleTypesBy(articleTypeName, articleTypeDescription, articleTypeMinimumStock);
       
       List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>();
        
        for(ArticleType at : a)
        {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            
            dto.setDescription(at.getDescription());
            dto.setId(at.getId());
            dto.setMinimumStock(at.getMinimumStock());
            dto.setName(at.getName());
            //dto.setStorageID(at.getStorage().getId());
            result.add(dto);
            
        }
        
        
        return result;
   }

    

    public int updateMinimumStock(int articleTypeId, int newMinStock) {
        
        ArticleType a = articleTypeDAO.findById(ArticleType.class, articleTypeId);
        
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
               ArticleTypeDTO dto = new ArticleTypeDTO();
        
            dto.setDescription(at.getDescription());
            dto.setName(at.getName());
            dto.setId(at.getId());
            dto.setMinimumStock(at.getMinimumStock());
            result.add(dto);
           }
        }
        
        return result;
    }

    public ArticleTypeDTO createNewArticleType(String name, String description) {
        
        ArticleType a = new ArticleType();
        
        a.setName(name);
        a.setDescription(description);
        a.setMinimumStock(0);
        articleTypeDAO.save(a);
        
        ArticleTypeDTO result = new ArticleTypeDTO();
        
        result.setDescription(a.getDescription());
        result.setName(a.getName());
        result.setId(a.getId());
        result.setMinimumStock(a.getMinimumStock());
 
        
        return result;
        
        
    }

    public List<ArticleTypeDTO> getAllArticleTypes(int storageID) {
        
        
        Storage s = storageDAO.findById(Storage.class, storageID);
        
        List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>();
        
        for(ArticleType at : s.getArticleTypes())
        {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            
            dto.setDescription(at.getDescription());
            dto.setId(at.getId());
            dto.setMinimumStock(at.getMinimumStock());
            dto.setName(at.getName());
            dto.setStorageID(at.getStorage().getId());
            result.add(dto);
            
        }
        
        
        return result;
        
    }

    public ArticleDTO createNewArticle(int articleTypeID) {
        
        
        Article a = new Article();
        
        a.setArticleType(articleTypeDAO.findById(ArticleType.class, articleTypeID));
        articleDAO.save(a);
        
        
        
        ArticleDTO dto = new ArticleDTO();
        
        dto.setArticleTypeID(a.getArticleType().getId());
        dto.setId(a.getId());
        
        
        
        return dto;
        
        
    }

    

    
    
}
