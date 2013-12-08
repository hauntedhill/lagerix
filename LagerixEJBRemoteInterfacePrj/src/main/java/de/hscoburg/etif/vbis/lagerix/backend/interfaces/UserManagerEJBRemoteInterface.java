package de.hscoburg.etif.vbis.lagerix.backend.interfaces;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
import java.util.List;
import javax.ejb.Remote;

/**
 * This EJB-Remote-Interface class manage all operations on an user object.
 *
 * @author zuch1000
 */
@Remote
public interface UserManagerEJBRemoteInterface {

    /**
     * This method search for an user by its unique email
     *
     * @param email - The email of the user
     * @return The data of the user or null if it not exists
     */
    public UserDTO find(String email);

    /**
     * This method registers an new User on the system
     *
     * @param user - A DTO with all the data of an user
     */
    public void register(UserDTO user);

    /**
     * This method returns all user in the system.
     *
     * @return A list with all user
     */
    public List<UserDTO> getAllUsers();

    /**
     * This method delete an user in the system.
     *
     * @param userName - The email of the user.
     */
    public void deleteUser(String userName);

    /**
     * This method update the groups of an user with the groups in the DTO
     *
     * @param user - The DTO with the mail of the user an the new groups
     */
    public void editUserGroups(UserDTO user);

    /**
     * This method check that the user logged in is in the passed group.
     *
     * @param group - The group to ne checked
     * @return true or false if not.
     */
    public boolean isInGroup(GroupType group);
}
