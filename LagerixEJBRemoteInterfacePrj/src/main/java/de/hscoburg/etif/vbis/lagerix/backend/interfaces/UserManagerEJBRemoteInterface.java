/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author zuch1000
 */
@Remote
public interface UserManagerEJBRemoteInterface {
    public UserDTO find(String email);
    
    
    public void register(UserDTO user);
    
    /**
 * Gets all Users
 * @return Returns all Users
*/
public List<UserDTO> getAllUsers();

/**
 * Deletes the user
 * @param userName The user to be deleted
*/
public void deleteUser(String userName);
  public void editUserGroups(UserDTO user);  
}
