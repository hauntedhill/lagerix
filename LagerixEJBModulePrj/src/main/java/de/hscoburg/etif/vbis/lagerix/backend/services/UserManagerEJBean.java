package de.hscoburg.etif.vbis.lagerix.backend.services;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.GroupDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
import de.hscoburg.etif.vbis.lagerix.backend.services.base.BaseService;
import de.hscoburg.etif.vbis.lagerix.backend.util.DTOConverter;
import de.hscoburg.etif.vbis.lagerix.backend.util.SHA512;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * This class manage all operations on an user object.
 *
 * @author zuch1000
 */
@Stateless
public class UserManagerEJBean extends BaseService implements UserManagerEJBRemoteInterface {

    @Resource
    private SessionContext sctx;

    /**
     * This method search for an user by its unique email
     *
     * @param email - The email of the user
     * @return The data of the user or null if it not exists
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public UserDTO find(String email) {
        User u = findUser(email);
        if (u != null) {
            /*UserDTO dto = new UserDTO();
             dto.setEmail(u.getEmail());
             dto.setFname(u.getFirstName());
             dto.setLname(u.getLastName());
        
             for(Groups g : u.getGroups()!=null?u.getGroups():new ArrayList<Groups>())
             {
             GroupDTO gDto = new GroupDTO();
             //gDto.setLagerId(g.get);
             gDto.setGroup(GroupType.valueOf(g.getGroups().name()));
             for(Storage s : g.getStorage()!=null?g.getStorage():new ArrayList<Storage>())
             {
             gDto.getStorageId().add(s.getId());
             }
            
             dto.getGroups().add(gDto);
             }*/

            return DTOConverter.convert(u);
        } else {
            return null;
        }
    }

    /**
     * This method update the groups of an user with the groups in the DTO
     *
     * @param user - The DTO with the mail of the user an the new groups
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void editUserGroups(UserDTO user) {
        User u = findUser(user.getEmail());

        if (u.getGroups() != null) {
            for (Groups g : u.getGroups()) {
                if (g.getStorage() != null) {
                    for (Storage s : g.getStorage()) {
                        if (s.getGroup() != null) {
                            s.getGroup().remove(g);
                            merge(s);
                        }
                    }

                }
                //g.getStorage().remove(g);

                remove(g);
            }
            u.getGroups().clear();
        }
        if (user.getGroups() != null) {
            for (GroupDTO g : user.getGroups()) {
                Groups group = new Groups();
                group.setGroups(Group.valueOf(g.getGroup().name()));
                if (g.getStorageId() != null) {

                    for (Integer storageId : g.getStorageId()) {
                        Storage s = findById(Storage.class, storageId);
                        s.addGroup(group);
                        group.addStorage(s);
                        save(group);
                        merge(s);
                    }

                }
                u.addGroup(group);
            }
        }

        merge(u);
    }

    /**
     * This method registers an new User on the system
     *
     * @param user - A DTO with all the data of an user
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void register(UserDTO user) {

        User u = new User();

        if (user.getPassword1() == null || user.getPassword1().length() == 0
                || !user.getPassword1().equals(user.getPassword2())) {
            throw new RuntimeException("Password 1 and Password 2 have to be equal (typo?)");
        }

        u.setEmail(user.getEmail());
        u.setFirstName(user.getFname());
        u.setLastName(user.getLname());

        if (user.getGroups() != null) {
            for (GroupDTO g : user.getGroups()) {
                Groups group = new Groups();
                group.setGroups(Group.valueOf(g.getGroup().name()));
                if (g.getStorageId() != null) {

                    for (Integer storageId : g.getStorageId()) {
                        Storage s = findById(Storage.class, storageId);
                        s.addGroup(group);
                        group.addStorage(s);
                        save(group);
                        merge(s);
                    }

                }
                u.addGroup(group);
            }
        }

        try {
            u.setPassword(SHA512.SHA512(user.getPassword1()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        u.setRegisteredOn(new Date());

        save(u);
    }

    /**
     * This method returns all user in the system.
     *
     * @return A list with all user
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public List<UserDTO> getAllUsers() {
        List<User> users = findAllUser();

        /* List<UserDTO> result = new ArrayList<UserDTO>();
        
         for(User u : users)
         {
         UserDTO dto = new UserDTO();
         dto.setEmail(u.getEmail());
         dto.setFname(u.getFirstName());
         dto.setLname(u.getLastName());
         result.add(dto);
            
         }*/
        return DTOConverter.convertUser(users);
    }

    /**
     * This method delete an user in the system.
     *
     * @param userName - The email of the user.
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void deleteUser(String userName) {

        User u = findUser(userName);
        if (u != null) {
            for (Groups g : u.getGroups()) {
                remove(g);
            }
            remove(u);
        }

    }

    /**
     * This method check that the user logged in is in the passed group.
     *
     * @param group - The group to ne checked
     * @return true or false if not.
     */
    public boolean isInGroup(GroupType group) {
        return sctx.isCallerInRole(group.name());
    }

}
