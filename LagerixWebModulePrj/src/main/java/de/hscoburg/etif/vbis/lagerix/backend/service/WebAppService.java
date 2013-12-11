package de.hscoburg.etif.vbis.lagerix.backend.service;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.*;
import java.util.Comparator;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import javax.ejb.EJB;
import java.util.List;

/**
 * Contains web services for Lagerix web application
 *
 * @author Tamás Ströber
 */
@Path("/secure/webApp")
public class WebAppService
{

    @EJB
    private ArticleManagerEJBRemoteInterface myArticleBean;
    @EJB
    private PlaceManagerEJBRemoteInterface myPlaceBean;
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WebAppService
     */
    public WebAppService()
    {
    }

    /**
     * Executes a simple search by id for an article type
     *
     * @param pId ID of the wanted article type
     * @return An ArticleTypeExtended object as an article type with additional
     * informations
     */
    @GET
    @Path("/simplesearch")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public ArticleTypeExtended simpleSearch(@QueryParam("ipIdSimpleSearch") String pId)
    {
        try
        {
            int id = Integer.parseInt(pId);
            ArticleTypeExtended tc = new ArticleTypeExtended();

            ArticleTypeDTO atDTO = this.myArticleBean.getArticleTypeByID(id);
            List<MovementDTO> mDTOs = this.myArticleBean.getMovementEntriesForArticleType(id);
            List<YardDTO> yfatDTOs = this.myPlaceBean.getYardsForArticleType(id);
            List<YardDTO> yDTOs = this.myPlaceBean.getAllYardsForStorage(atDTO.getStorageID());

            tc.setMovements(mDTOs);
            tc.setDescription(atDTO.getDescription());
            tc.setId(atDTO.getId());
            tc.setName(atDTO.getName());
            tc.setMinimumStock(atDTO.getMinimumStock());
            tc.setStorageId(atDTO.getStorageID());
            tc.setCurrentStock(yfatDTOs.size());
            tc.setStorageName(this.myPlaceBean.getStorage(tc.getStorageId()).getName());
            return tc;
        } catch (Exception e)
        {
            return new ArticleTypeExtended();
        }
    }

    /**
     * Executes an advanced search for article types that are correspond to the
     * given params. Supports wildcard search.
     *
     * @param pName string which is searched in the article type name
     * @param pDescription string which is searched in the article type
     * description
     * @param pMinimumStock minimum stock of the wanted article types
     * @return A List of ArticleTypeDTOs that are correspond to the given params
     */
    @GET
    @Path("/advancedsearch")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<ArticleTypeDTO> advancedSearch(@QueryParam("ipNameAdvancedSearch") String pName, @QueryParam("ipNoteAdvancedSearch") String pDescription, @QueryParam("ipMinimumStockAdvancedSearch") String pMinimumStock)
    {
        try
        {
            List<ArticleTypeDTO> result = this.myArticleBean.searchArticleType(pName, pDescription, pMinimumStock);
            return result;
        } catch (Exception e)
        {
            return new java.util.ArrayList<ArticleTypeDTO>();
        }
    }

    /**
     * Returns all article types with underrun minimum stock.
     *
     * @return A List of ArticleTypeDTOs containing all article types with
     * underrun minimum stock
     */
    @GET
    @Path("/underrunminstocks")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<ArticleTypeDTO> getArticleTypesWithUnderrunMinStock()
    {
        try
        {
            List<ArticleTypeDTO> result = this.myArticleBean.getAllArticleTypesWithUnderrunMinStock();
            return result;
        } catch (Exception e)
        {
            return new java.util.ArrayList<ArticleTypeDTO>();
        }
    }

    /**
     * Changes the minimum stock of an article type.
     *
     * @param pId ID of the article type which is to be changed
     * @param pMinStock the new minimum stock
     * @return
     */
    @POST
    @Path("/minimumstock")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.TEXT_PLAIN)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public int changeMinimumStock(@FormParam("id") String pId, @FormParam("ipMinimumStock") String pMinStock)
    {
        try
        {
            int id = Integer.parseInt(pId);
            int minStock = Integer.parseInt(pMinStock);
            int result = this.myArticleBean.updateMinimumStock(id, minStock);
            return result;
        } catch (Exception e)
        {
            return 1;
        }
    }

    /**
     * Only for testing REST service
     *
     * @return "Test OK" when it works
     */
    @GET
    @Path("/test")
    public String test()
    {
        return "Test OK";
    }

    /**
     * Gives advanced informations of a storage back.
     *
     * @param pId ID of the wanted storage
     * @return Returns a StorageExtended object which advanced storage
     * informations.
     */
    @GET
    @Path("/storage")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public StorageExtended getStorage(@QueryParam("storageId") String pId)
    {
        int storageId = Integer.parseInt(pId);
        StorageExtended storageEx = new StorageExtended();
        storageEx.setId(storageId);
        storageEx.setName(this.myPlaceBean.getStorage(storageId).getName());
        storageEx.setYards(new ArrayList<YardExtended>());

        List<YardDTO> yards = this.myPlaceBean.getAllYardsForStorage(storageId);
        List<ArticleTypeDTO> articleTypes = this.myArticleBean.getAllArticleTypes(storageId);

        for (ArticleTypeDTO articleType : articleTypes)
        {
            List<ArticleDTO> articles = this.myArticleBean.getAllArticleByArticleType(articleType.getId());
            for (ArticleDTO article : articles)
            {
                if (article.getYardID() != 0)
                {
                    for (YardDTO yard : yards)
                    {
                        if (yard.getId() == article.getYardID())
                        {
                            YardExtended yardEx = new YardExtended();
                            yardEx.setId(article.getYardID());
                            yardEx.setArticleId(article.getId());
                            yardEx.setArticleTypeId(articleType.getId());
                            yardEx.setArticleTypeName(articleType.getName());
                            storageEx.getYards().add(yardEx);
                            yard.setId(0);
                            break;
                        }
                    }
                }
            }
        }
        for (YardDTO yard : yards)
        {
            if (yard.getId() != 0)
            {
                YardExtended yardEx = new YardExtended();
                yardEx.setId(yard.getId());
                yardEx.setArticleId(0);
                yardEx.setArticleTypeId(0);
                yardEx.setArticleTypeName("");
                storageEx.getYards().add(yardEx);
                yard.setId(0);
            }
        }
        Collections.sort(storageEx.getYards(), new YardComparator());
        return storageEx;
    }

    /**
     * Gives all storgaes back in a list.
     *
     * @return Returns a list of all storages.
     */
    @GET
    @Path("/storages")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<StorageDTO> getStorages()
    {
        return this.myPlaceBean.getAllStorages();
    }
}

/**
 * Comparator for YardExtended objects.
 * @author Tamás Ströber
 */
class YardComparator implements Comparator<YardExtended>
{

    @Override
    public int compare(YardExtended pYard1, YardExtended pYard2)
    {
        if (pYard1.getId() < pYard2.getId())
        {
            return -1;
        }
        if (pYard1.getId() == pYard2.getId())
        {
            return 0;
        }
        return 1;
    }
}

/**
 * A StorageExtended object is like a StorageDTO object but has instead of a List<YardDTO> a List<YardExtended> as member.
 * @author Tamás Ströber
 */
class StorageExtended implements Serializable
{

    private int id;
    private String name;
    private List<YardExtended> yards;
///<editor-fold defaultstate="collapsed" desc="getter and setter">

    public List<YardExtended> getYards()
    {
        return yards;
    }

    public void setYards(List<YardExtended> yards)
    {
        this.yards = yards;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int storageId)
    {
        this.id = storageId;
    }
//</editor-fold>
}

/**
 * An YardExtended object contains informations about an yard.
 * @author Tamás Ströber
 */
class YardExtended implements Serializable
{

    private int id;
    /**
     * The ID of an article which is incorporated in this yard. 0 if Yard is emty.
     */    
    private int articleId;
    /**
     * The ID of the article type of the incorporated article. 0 if Yard is emty.
     */
    private int articleTypeId;
    /**
     *  Name of the article type of the incorporated article.
     */
    private String articleTypeName;
///<editor-fold defaultstate="collapsed" desc="getter and setter">

    public String getArticleTypeName()
    {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName)
    {
        this.articleTypeName = articleTypeName;
    }

    public int getArticleTypeId()
    {
        return articleTypeId;
    }

    public void setArticleTypeId(int articleTypeId)
    {
        this.articleTypeId = articleTypeId;
    }

    public int getArticleId()
    {
        return articleId;
    }

    public void setArticleId(int articleId)
    {
        this.articleId = articleId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
//</editor-fold>
}

/**
 * A ArticleTypeExtended object contains basic and extended informations about an article type.
 * @author Tamás Ströber
 */
class ArticleTypeExtended implements Serializable
{

    private int id;
    private String name;
    private String description;
    private int minimumStock;
    /**
     * ID of the storage in which an article of this article type can be incorporated.
     */
    private int storageId;
    private String storageName;
    private int currentStock;
    /**
     * List of all movements of this article type.
     */
    private List<MovementDTO> movements;
///<editor-fold defaultstate="collapsed" desc="getter and setter">

    public List<MovementDTO> getMovements()
    {
        return movements;
    }

    public void setMovements(List<MovementDTO> movements)
    {
        this.movements = movements;
    }

    public int getCurrentStock()
    {
        return currentStock;
    }

    public void setCurrentStock(int currentStock)
    {
        this.currentStock = currentStock;
    }

    public String getStorageName()
    {
        return storageName;
    }

    public void setStorageName(String storageName)
    {
        this.storageName = storageName;
    }

    public int getStorageId()
    {
        return storageId;
    }

    public void setStorageId(int storageId)
    {
        this.storageId = storageId;
    }

    public int getMinimumStock()
    {
        return minimumStock;
    }

    public void setMinimumStock(int minimumStock)
    {
        this.minimumStock = minimumStock;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
//</editor-fold>
}
