/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.services;

import de.hscoburg.etif.vbis.lagerix.backend.dao.StorageDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.UserDAO;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.GroupDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.util.SHA512;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;


/**
 *
 * @author zuch1000
 */
@Stateless
public class UserManagerEJBean implements UserManagerEJBRemoteInterface{

    
    @EJB
    private UserDAO userDAO;
    
    @EJB
    private StorageDAO storageDAO;

    public UserDTO find(String email) {
        User u = userDAO.find(email);
        if(u!=null)
        {
        UserDTO dto = new UserDTO();
        dto.setEmail(u.getEmail());
        dto.setFname(u.getFirstName());
        dto.setLname(u.getLastName());
        
        for(Groups g : u.getGroups()!=null?u.getGroups():new ArrayList<Groups>())
        {
            GroupDTO gDto = new GroupDTO();
            //gDto.setLagerId(g.get);
            gDto.setGroup(g.getGroups().name());
            for(Storage s : g.getStorage()!=null?g.getStorage():new ArrayList<Storage>())
            {
                gDto.getStorageId().add(s.getId());
            }
            
            dto.getGroups().add(gDto);
        }
        
                
        return dto;
        }
        else
        {
            return null;
        }
    }

    public void register(UserDTO user) {
        
        
        User u = new User();
         
        if (user.getPassword1() == null || user.getPassword1().length() == 0
                || !user.getPassword1().equals(user.getPassword2()) )
            throw new RuntimeException("Password 1 and Password 2 have to be equal (typo?)");
         
        u.setEmail( user.getEmail());
        u.setFirstName(user.getFname());
        u.setLastName( user.getLname());   
        
        
        
        
        
        if(user.getGroups() != null)
        {
            for(GroupDTO g : user.getGroups())
            {
                Groups group = new Groups();
                group.setGroups(Group.valueOf(g.getGroup()));
                if(g.getStorageId()!=null)
                {
                    
                    for(Integer storageId:g.getStorageId())
                    {
                       Storage s = storageDAO.findById(Storage.class, storageId) ;
                       s.addGroup(group);
                       group.addStorage(s);
                       userDAO.save(group);
                       storageDAO.merge(s);
                    }
                    
                
                 }
              u.addGroup(group);
            }
        }
        
        //Groups  g1 = new Groups();
        //g1.setGroups(Group.BENUTZER);
        
        //u.addGroup(g1);
        //userDAO.save(g1);
        //g1 = new Groups();
        //g1.setGroups(Group.ADMINISTRATOR);
        //userDAO.save(g1);
        //u.addGroup(g1);
        
        
        
        
        try
        {
        u.setPassword( SHA512.SHA512(user.getPassword1() ));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        u.setRegisteredOn(new Date());
        
        userDAO.save(u);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.findAll();
        
        List<UserDTO> result = new ArrayList<UserDTO>();
        
        for(User u : users)
        {
            UserDTO dto = new UserDTO();
            dto.setEmail(u.getEmail());
            dto.setFname(u.getFirstName());
            dto.setLname(u.getLastName());
            result.add(dto);
            
        }
        return result;
    }

    @RolesAllowed("ADMINISTRATOR")
    public void deleteUser(String userName) {
        
        User u = userDAO.find(userName);
        if(u!=null)
        {
        for(Groups g : u.getGroups())
        {
            userDAO.remove(g);
        }
        userDAO.remove(u);
        }
        
        
        
    }
        
        
    
}
