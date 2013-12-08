package de.hscoburg.etif.vbis.lagerix.backend.interfaces;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 * This EJB-Remote-Interface class manage all operations on yards and storage entities.
 *
 * @author zuch1000
 */
@Remote
public interface PlaceManagerEJBRemoteInterface {

    /**
     * This method create an new Yard for the passed storage
     *
     * @param storageID - The id f the storage
     * @return The created yard with the ID
     */
    public YardDTO createNewYard(int storageID);

    /**
     * This method delets an existing yard.
     *
     * @param yardID - The id of the yard to be delete
     */
    public void deleteYard(int yardID);

    /**
     * This method returns all yards for an storage.
     *
     * @param storageID - The id of the storage
     * @return A list with all yards from the storage
     */
    public List<YardDTO> getAllYards(int storageID);

    /**
     * This methid creates an new storage
     *
     * @param name - The name of the storage
     * @return The created storage with ID
     */
    public StorageDTO createNewStorage(String name);

    /**
     * This storage deletes a storage.
     *
     * @param storageID - The id of the storae to delete
     */
    public void deleteStorage(int storageID);

    /**
     * This method returns all storages of the user logged in.
     *
     * @return A list with all storages of the user
     */
    public List<StorageDTO> getStorages();

    /**
     * This methods returns the data of the storage.
     *
     * @param storageId - The id of the storage
     * @return A DTO object with the data of the storage.
     */
    public StorageDTO getStorage(int storageId);

    /**
     * This method return all storages
     *
     * @return A list with all storages.
     */
    public List<StorageDTO> getAllStorages();

    /**
     * This method returns all yards of an storage
     *
     * @param storageID - The id of the storage
     * @return A list with all yards from the storage.
     */
    public List<YardDTO> getAllYardsForStorage(int storageID);

    /**
     * This method returns all yards where an article with the specified articleType is stored.
     *
     * @param articleTypeID - The articleTypeId
     * @return A list with all Yards.
     */
    public List<YardDTO> getYardsForArticleType(int articleTypeID);
}
