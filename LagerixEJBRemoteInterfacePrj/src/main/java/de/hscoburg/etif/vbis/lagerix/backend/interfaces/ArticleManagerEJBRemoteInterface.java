/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.interfaces;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 * EJB-Remote-Interface class for all operations on an Article, Movements or ArticleType.
 *
 * @author zuch1000
 */
@Remote
public interface ArticleManagerEJBRemoteInterface {

    /**
     * Method for realising or incorporating of an article
     *
     * @param entry - An DTO with alle information like the articleId and the movement type
     * @param yardID - the Id of the yard where the article should be released or incorporated
     * @return 0 if success, 1 on failure
     */
    public int saveMovementEntry(MovementDTO entry, int yardID);

    /**
     * This methods search for all movement entries of an articleType
     *
     * @param articleTypeID - The articleypeId to search after the articles and movements
     * @return A list with all movements for this articleType
     */
    public List<MovementDTO> getMovementEntriesForArticleType(int articleTypeID);

    /**
     * This method search for an article by the ID of it
     *
     * @param articleTypeID - The articleType to search for it
     * @return The article or null if the id doesnt exists
     */
    public ArticleTypeDTO getArticleTypeByID(int articleTypeID);

    /**
     * This method search an article for the parameters passing. If a parameter is null or empty it will be ignored. The parameter are connected with an logical
     * AND and the article must contains the string in its attribute.
     *
     * @param articleTypeName - The name of the article
     * @param articleTypeDescription - The description of the article
     * @param articleTypeMinimumStock - The minimum stock of an article
     * @return A list with all articles found for the parameter passing.
     */
    public List<ArticleTypeDTO> searchArticleType(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock);

    /**
     * This method updates the minimum stock of an articleType.
     *
     * @param articleTypeId - The articleType to update the minimum stock
     * @param newMinStock - The minimum stock to set.
     * @return 0 for success, 1 for failure
     */
    public int updateMinimumStock(int articleTypeId, int newMinStock);

    /**
     * This methods returns all ArticleTypes where the count of all articles for the storage is less than the minimumstock
     *
     * @return A list with all articlesTypes
     */
    public List<ArticleTypeDTO> getAllArticleTypesWithUnderrunMinStock();

    /**
     * This method creates an articleType with the parameter.
     *
     * @param name - The name of the articleType
     * @param description - The description of the articleType
     * @param storageId - The storageID for the new articleType
     * @return The created articleType with ID
     */
    public ArticleTypeDTO createNewArticleType(String name, String description, int storageId);

    /**
     * This method returns all articleTypes for an storage
     *
     * @param storageID - The storage to search for articleTypes
     * @return A list with all articleTypes of the storage
     */
    public List<ArticleTypeDTO> getAllArticleTypes(int storageID);

    /**
     * This method creates an new article for an given articleType
     *
     * @param articleTypeID - The articleTypeId of the new article
     * @return The created article with ID
     */
    public ArticleDTO createNewArticle(int articleTypeID);

    /**
     * This method deletes the articleType with the passed id.
     *
     * @param articleTypeid - The id of the articleType to be delete
     */
    public void deleteArticleType(int articleTypeid);

    /**
     * This method updates an existing articleType
     *
     * @param articleType - The DTO with alle the attributes(name and description) to update the articleType with the ID.
     */
    public void updateArticleType(ArticleTypeDTO articleType);

    /**
     * This methods delete an article with the id.
     *
     * @param articleId - The id of the article to be delete
     */
    public void deleteArticle(int articleId);

    /**
     * This method return all article for an articleType
     *
     * @param articleTypeId - The articleTypeId
     * @return A list with all articles for the articleType
     */
    public List<ArticleDTO> getAllArticleByArticleType(int articleTypeId);

}
