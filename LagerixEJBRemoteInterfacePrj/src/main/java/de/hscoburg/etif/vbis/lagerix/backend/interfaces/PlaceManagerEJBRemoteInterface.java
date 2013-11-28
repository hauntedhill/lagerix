/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author zuch1000
 */
@Remote
public interface PlaceManagerEJBRemoteInterface {
 /**
 * Creates a new StorageLocation
 * @param storageID id of the storage
 * @return The newly created StorageLocation
*/
public YardDTO createNewYard(int storageID);

/**
 * Deletes a StorageLocation
 * @param storageLocationID id of the storage location
*/
public void deleteYard(int yardID);

/**
 * Gets all StoragesLocations
 * @param storageID id of the storage
 * @return A List of all StorageLocations for the Storage
*/
public List<YardDTO> getAllYards(int storageID);

/**
 * Creates a new Storage
 * @param name name of the storage
 * @return The newly created Storage
*/
public StorageDTO createNewStorage(String name);

/**
 * Deletes a Storage
 * @param storageID 
*/
public void deleteStorage(int storageID);

/**
 * Gets the associated Storage for the logged in User
 * @return The Storage Object for which the user is in charge
*/
public StorageDTO getStorage();

/**
 * Gets all Storages
 * @return Returns all available Storages
*/
public List<StorageDTO> getAllStorages();

/**
 * Returns all storage locations for a specific storage
 * @param storageID ID of the requested storage
 * @return List of all storage locations for the requested storage
 */
public List<YardDTO> getAllStorageLocationsForStorage(int storageID);
/**
 * Returns all storage locations where a specified article type if currently stored
 * @param articleTypeID ID of the requested article type
 * @return List of storage locations
 */
public List<YardDTO> getLocationsForArticleType(int articleTypeID);
}
