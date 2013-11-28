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
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author zuch1000
 */
@Stateless
public class PlaceManagerEJBean implements PlaceManagerEJBRemoteInterface{
    
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
}
