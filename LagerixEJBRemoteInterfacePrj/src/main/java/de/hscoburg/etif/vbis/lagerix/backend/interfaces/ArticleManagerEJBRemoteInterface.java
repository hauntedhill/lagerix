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
public int saveMovementEntry(MovementDTO entry);

/**
 * Returns all book entries for a specific article type
 * @param articleTypeID ID of the requested article type
 * @return List of book entries for the requested article type
 */
public List<MovementDTO> getMovementEntriesForArticleType(int articleTypeID);

/**
 * Returns an article type specified by it's ID
 * @param articleTypeID ID of the requested article type
 * @return Object of type ArticleType
 */
public ArticleTypeDTO getArticleTypeByID(int articleTypeID);

/**
 * Returns an article type specified by it's name
 * @param articleTypeName name of the requested article type
 * @return Object of type ArticleType
 */
public List<ArticleTypeDTO> searchArticleType(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock);





/**
 * Updates the minimumStock value of an ArticleType.
 * @param articleTypeId ID of the ArticleType which is going to be updated
 * @param newMinStock The new value of minimumStock.
 * @return 0 if everything went fine otherwise an error code
 */
public int updateMinimumStock(int articleTypeId, int newMinStock);

/**
 * Returns all ArticleTypes with underrun minimumStock.
 * @return A List of ArticleTypes with underrun minimumStock
 */
public List<ArticleTypeDTO> getAllArticleTypesWithUnderrunMinStock();




/**
 * Creates a new ArticleType and return it
 * @param name name of the new ArticleType
 * @param description description of the new ArticleType 
 * @param storageId id of the storage
 * @return The newly created ArticleType
*/
public ArticleTypeDTO createNewArticleType(String name, String description, int storageId);

/**
 * Return all Article Types within the storage
 * @param storageID id of the storage 
 * @return A List of all ArticleTypes in the storage
*/
public List<ArticleTypeDTO> getAllArticleTypes(int storageID);

/**
 * Creates a new Article
 * @param articleTypeID articletype ID
 * @return The newly created Article
*/
public ArticleDTO createNewArticle(int articleTypeID);





}
