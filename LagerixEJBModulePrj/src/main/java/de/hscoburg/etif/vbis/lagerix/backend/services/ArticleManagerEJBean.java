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
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
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
        dto.setStorageID(at.getStorage().getId());
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

    public YardDTO createNewYard(int storageID) {
        Storage s = storageDAO.findById(Storage.class, storageID);
        
        
        
        Yard y = new Yard();
        y.setStorage(s);
        yardDAO.save(y);
        
        YardDTO dto = new YardDTO();
        
        dto.setId(y.getId());
        dto.setStorageID(y.getStorage().getId());
        
        return dto;
        
        
    }

    public void deleteYard(int yardID) {
        Yard y = yardDAO.findById(Yard.class, yardID);
        yardDAO.remove(y);
    }

    public List<YardDTO> getAllYards(int storageID) {
        Storage s = storageDAO.findById(Storage.class, storageID);
        
        List<YardDTO> result = new ArrayList<YardDTO>();
        
        
        for(Yard y : s.getYards())
        {
           YardDTO dto = new YardDTO();
           
           dto.setId(y.getId());
           dto.setStorageID(y.getStorage().getId());
           result.add(dto);
        }
        
        return result;
        
    }

    public StorageDTO createNewStorage(String name) {
        
        Storage s = new Storage();
        
        s.setName(name);
        
        storageDAO.save(s);
        
        
        
        StorageDTO dto = new StorageDTO();
        
        dto.setName(s.getName());
        dto.setId(s.getId());
        
        return dto;
       
        
    }

    public void deleteStorage(int storageID) {
        storageDAO.remove(storageDAO.findById(Storage.class, storageID));
    }

    public StorageDTO getStorage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<StorageDTO> getAllStorages() {
        
        List<StorageDTO> result = new ArrayList<StorageDTO>();
        for(Storage s : storageDAO.getAllStorages())
        {
            
            StorageDTO dto = new StorageDTO();
            
            dto.setId(s.getId());
            dto.setName(s.getName());
            List<YardDTO> yardResult = new ArrayList<YardDTO>();
            for(Yard y : s.getYards())
            {
                YardDTO yardDTO = new YardDTO();
                
                yardDTO.setId(y.getId());
                yardDTO.setStorageID(y.getStorage().getId());
                yardResult.add(yardDTO);
            }
            dto.setYards(yardResult);
            result.add(dto);
            
            
        }
        
        return result;
    }

    
    
}
