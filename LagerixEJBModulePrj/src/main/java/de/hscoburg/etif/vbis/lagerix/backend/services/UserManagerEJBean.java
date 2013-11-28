/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.services;

import de.hscoburg.etif.vbis.lagerix.backend.dao.UserDAO;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.util.SHA512;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author zuch1000
 */
@Stateless
public class UserManagerEJBean implements UserManagerEJBRemoteInterface{

    
    @EJB
    private UserDAO userDAO;

    public UserDTO find(String email) {
        User u = userDAO.find(email);
        if(u!=null)
        {
        UserDTO dto = new UserDTO();
        dto.setEmail(u.getEmail());
        dto.setFname(u.getFirstName());
        dto.setLname(u.getLastName());
                
                
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
        
        
        List<Groups> groups = new ArrayList<Groups>();
        
        
        Groups  g1 = new Groups();
        g1.setGroups(Group.BENUTZER);
        
        u.addGroup(g1);
        userDAO.save(g1);
        g1 = new Groups();
        g1.setGroups(Group.ADMINISTRATOR);
        userDAO.save(g1);
        u.addGroup(g1);
        
        
        
        u.setGroups(groups);
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
        
        
    
}
