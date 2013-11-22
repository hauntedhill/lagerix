/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.backend.interfaces;

import java.util.List;
import javax.ejb.Remote;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.*;

/**
 *
 * @author zuch1000
 */
@Remote
public interface ArticleManagerEJBRemoteInterface {
  /**
 * Saves a book entry corresponding to a specific article which the user scanned using the Android application
 * @param entry A book entry containing information about booking process such as the ID of the corresponding article and location 
 * @return 0 if everything went fine or an error code
 */
public int saveBookEntry(BookEntry entry);

/**
 * Returns all book entries for a specific article type
 * @param articleTypeID ID of the requested article type
 * @return List of book entries for the requested article type
 */
public List<BookEntry> getBookEntriesForArticleType(int articleTypeID);

/**
 * Returns an article type specified by it's ID
 * @param articleTypeID ID of the requested article type
 * @return Object of type ArticleType
 */
public ArticleType getArticleTypeByID(int articleTypeID);

/**
 * Returns an article type specified by it's name
 * @param articleTypeName name of the requested article type
 * @return Object of type ArticleType
 */
public ArticleType getArticleTypeByName(String articleTypeName);

/**
 * Returns all storage locations where a specified article type if currently stored
 * @param articleTypeID ID of the requested article type
 * @return List of storage locations
 */
public List<StorageLocation> getLocationsForArticleType(int articleTypeID);

/**
 * Returns all storage locations for a specific storage
 * @param storageID ID of the requested storage
 * @return List of all storage locations for the requested storage
 */
public List<StorageLocation> getAllStorageLocationsForStorage(int storageID);  
}
