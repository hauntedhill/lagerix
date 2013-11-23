/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import javax.ejb.Remote;

/**
 *
 * @author zuch1000
 */
@Remote
public interface UserManagerEJBRemoteInterface {
    public UserDTO find(String email);
    
    
    public void register(UserDTO user);
    
}
