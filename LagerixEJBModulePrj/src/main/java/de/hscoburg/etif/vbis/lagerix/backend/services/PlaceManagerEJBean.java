/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.services;

import de.hscoburg.etif.vbis.lagerix.backend.dao.ArticleDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.ArticleTypeDAO;

import de.hscoburg.etif.vbis.lagerix.backend.dao.StorageDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.UserDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.YardDAO;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import de.hscoburg.etif.vbis.lagerix.backend.util.DTOConverter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
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
    private ArticleTypeDAO articleTypeDAO;
    
    @EJB
    private UserDAO userDAO;
      
    @EJB
    private StorageDAO storageDAO;
       
       
     @Resource 
     SessionContext scxt;  
       
       
   public YardDTO createNewYard(int storageID) {
        Storage s = storageDAO.findById(Storage.class, storageID);
        
        
        
        Yard y = new Yard();
        s.addYard(y);
        yardDAO.save(y);
        storageDAO.merge(s);
        
        
        
        return DTOConverter.convert(y);
        
        
    }

    public void deleteYard(int yardID) {
        Yard y = yardDAO.findById(Yard.class, yardID);
        yardDAO.remove(y);
    }

    public List<YardDTO> getAllYards(int storageID) {
        Storage s = storageDAO.findById(Storage.class, storageID);
        
       
        
        
        return DTOConverter.convertYard(s.getYards());
        
    }

    public StorageDTO createNewStorage(String name) {
        
        Storage s = new Storage();
        
        s.setName(name);
        
        storageDAO.save(s);
        
        
        
       
        
        return DTOConverter.convert(s);
       
        
    }

    public void deleteStorage(int storageID) {
        storageDAO.remove(storageDAO.findById(Storage.class, storageID));
    }

    public List<StorageDTO> getStorages() {
        
        User u = userDAO.find(scxt.getCallerPrincipal().getName());
        
        List<Storage> storages = new ArrayList<Storage>();
        
        if(u.getGroups()!=null)
        {
            for(Groups g : u.getGroups())
            {
                storages.addAll(g.getStorage());
            }
        }
        
        return DTOConverter.convertStorage(storages);
    }

    public List<StorageDTO> getAllStorages() {
        
       
        
        return DTOConverter.convertStorage(storageDAO.getAllStorages());
    } 
    
    public List<YardDTO> getYardsForArticleType(int articleTypeID) {
         
        
       
        return DTOConverter.convertYard(yardDAO.getYardsForArticleType(articleTypeID));
        
    }

    public List<YardDTO> getAllYardsForStorage(int storageID) {
         
        
        Storage storage = storageDAO.findById(Storage.class, storageID);
        
       
        
        return DTOConverter.convertYard(storage.getYards());
    
    
    }
}
