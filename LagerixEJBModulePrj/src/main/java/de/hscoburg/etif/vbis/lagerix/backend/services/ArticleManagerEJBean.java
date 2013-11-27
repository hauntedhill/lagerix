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
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
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
    
    
    public int saveBookEntry(MovementDTO entry) {
        Yard yard = yardDAO.findById(Yard.class, entry.getYardID());
        
        
        Article article = articleDAO.findById(Article.class, entry.getArticleID());
        
        
        Movement m = new Movement();
        if(entry.isBookedIn())
        {
            m.setMovement(Movements.INCORPORATE);
        }
        else
        {
            m.setMovement(Movements.RELEASE);
        }
        m.setArticle(article);
        m.setTime(new Date());
        article.setYard(yard);
        
        movementDAO.save(m);
        articleDAO.merge(article);
        
        return 0;
        
    }

    public List<MovementDTO> getBookEntriesForArticleType(int articleTypeID) {
        
        List<MovementDTO> entries = new ArrayList<MovementDTO>();
        
        return entries;
    }

    public ArticleTypeDTO getArticleTypeByID(int articleTypeID) {
        ArticleType at = articleTypeDAO.findById(ArticleType.class, articleTypeID);
        
        
        ArticleTypeDTO dto = new ArticleTypeDTO();
        
        dto.setId(at.getId());
        dto.setDescription(at.getDescription());
        dto.setName(at.getName());
        dto.setYardID(at.getStorage().getId());
        dto.setMinimumStock(at.getMinimumStock());
        return dto;
        
        
    }

   public List<ArticleTypeDTO> searchArticleType(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock)
   {
       return null;
   }

    public List<YardDTO> getLocationsForArticleType(int articleTypeID) {
         
        
        Article a =  articleDAO.findById(Article.class, articleTypeID);
        
        List<YardDTO> result = new ArrayList<YardDTO>();
        
        //for(Yard y : a.)
        //{
            
        //}
        return result;
        
    }

    public List<YardDTO> getAllStorageLocationsForStorage(int storageID) {
         
        
        Storage storage = storageDAO.findById(Storage.class, storageID);
        
        List<YardDTO> result = new ArrayList<YardDTO>();
        
        for(Yard y : storage.getYards())
        {
            YardDTO e = new YardDTO();
            
            e.setStorageID(y.getId());
            result.add(e);
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
        return new ArrayList<ArticleTypeDTO>();
    }
    
}
