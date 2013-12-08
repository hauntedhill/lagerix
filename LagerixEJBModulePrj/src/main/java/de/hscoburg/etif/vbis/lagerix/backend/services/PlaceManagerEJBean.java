package de.hscoburg.etif.vbis.lagerix.backend.services;

import de.hscoburg.etif.vbis.lagerix.backend.dao.ArticleTypeDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.StorageDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.UserDAO;
import de.hscoburg.etif.vbis.lagerix.backend.dao.YardDAO;
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
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * This class manage all operations on yards and storage entities.
 *
 * @author zuch1000
 */
@Stateless
public class PlaceManagerEJBean implements PlaceManagerEJBRemoteInterface {

    @EJB
    private YardDAO yardDAO;

    @EJB
    private ArticleTypeDAO articleTypeDAO;

    @EJB
    private UserDAO userDAO;

    @EJB
    private StorageDAO storageDAO;

    @Resource
    private SessionContext scxt;

    /**
     * This method create an new Yard for the passed storage
     *
     * @param storageID - The id f the storage
     * @return The created yard with the ID
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public YardDTO createNewYard(int storageID) {
        Storage s = storageDAO.findById(Storage.class, storageID);

        Yard y = new Yard();
        s.addYard(y);
        yardDAO.save(y);
        storageDAO.merge(s);

        return DTOConverter.convert(y);

    }

    /**
     * This method delets an existing yard.
     *
     * @param yardID - The id of the yard to be delete
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public void deleteYard(int yardID) {
        Yard y = yardDAO.findById(Yard.class, yardID);
        if (y.getStorage() != null) {
            y.getStorage().getYards().remove(y);
        }
        yardDAO.remove(y);
    }

    /**
     * This method returns all yards for an storage.
     *
     * @param storageID - The id of the storage
     * @return A list with all yards from the storage
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public List<YardDTO> getAllYards(int storageID) {
        Storage s = storageDAO.findById(Storage.class, storageID);

        return DTOConverter.convertYard(s.getYards());

    }

    /**
     * This methid creates an new storage
     *
     * @param name - The name of the storage
     * @return The created storage with ID
     */
    @RolesAllowed({"LAGERVERWALTER", "ADMINISTRATOR"})
    public StorageDTO createNewStorage(String name) {

        Storage s = new Storage();

        s.setName(name);

        storageDAO.save(s);

        return DTOConverter.convert(s);

    }

    /**
     * This storage deletes a storage.
     *
     * @param storageID - The id of the storae to delete
     */
    @RolesAllowed({"LAGERVERWALTER", "ADMINISTRATOR"})
    public void deleteStorage(int storageID) {
        storageDAO.remove(storageDAO.findById(Storage.class, storageID));
    }

    /**
     * This method returns all storages of the user logged in.
     *
     * @return A list with all storages of the user
     */
    @RolesAllowed({"LAGERVERWALTER"})
    public List<StorageDTO> getStorages() {

        User u = userDAO.find(scxt.getCallerPrincipal().getName());

        List<Storage> storages = new ArrayList<Storage>();

        if (u.getGroups() != null) {
            for (Groups g : u.getGroups()) {
                storages.addAll(g.getStorage());
            }
        }

        return DTOConverter.convertStorage(storages);
    }

    /**
     * This method return all storages
     *
     * @return A list with all storages.
     */
    @RolesAllowed({"LAGERVERWALTER", "ADMINISTRATOR"})
    public List<StorageDTO> getAllStorages() {

        return DTOConverter.convertStorage(storageDAO.getAllStorages());
    }

    /**
     * This method returns all yards where an article with the specified articleType is stored.
     *
     * @param articleTypeID - The articleTypeId
     * @return A list with all Yards.
     */
    @RolesAllowed({"EINKAEUFER", "LAGERVERWALTER"})
    public List<YardDTO> getYardsForArticleType(int articleTypeID) {

        return DTOConverter.convertYard(yardDAO.getYardsForArticleType(articleTypeID));

    }

    /**
     * This method returns all yards of an storage
     *
     * @param storageID - The id of the storage
     * @return A list with all yards from the storage.
     */
    @RolesAllowed({"EINKAEUFER", "LAGERVERWALTER"})
    public List<YardDTO> getAllYardsForStorage(int storageID) {

        Storage storage = storageDAO.findById(Storage.class, storageID);

        return DTOConverter.convertYard(storage.getYards());

    }

    /**
     * This methods returns the data of the storage.
     *
     * @param storageId - The id of the storage
     * @return A DTO object with the data of the storage.
     */
    @RolesAllowed({"LAGERVERWALTER", "ADMINISTRATOR"})
    public StorageDTO getStorage(int storageId) {
        return DTOConverter.convert(storageDAO.findById(Storage.class, storageId));
    }
}
