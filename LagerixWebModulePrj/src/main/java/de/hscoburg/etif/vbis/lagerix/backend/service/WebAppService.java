/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.*;
import javax.xml.bind.annotation.XmlRootElement;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import java.util.List;

/**
 * REST Web Service
 *
 * @author Tamas
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
            tc.setCurrentStock(yDTOs.size());
            StorageDTO myStorage = null;
            List<StorageDTO> storages = this.myPlaceBean.getAllStorages();
            for (StorageDTO storage : storages)
            {
                if (storage.getId() == tc.getStorageId())
                {
                    myStorage = storage;
                    break;
                }
            }
            tc.setStorageName(myStorage == null ? "kein Lagername" : myStorage.getName());
            return tc;
        } catch (Exception e)
        {
            return new ArticleTypeExtended();
        }
    }

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

    @GET
    @Path("/currentstock")
    @Produces(MediaType.TEXT_PLAIN)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public int currentStock(@QueryParam("id") String pId)
    {
        try
        {
            int id = Integer.parseInt(pId);
            List<YardDTO> result = this.myPlaceBean.getYardsForArticleType(id);
            return result.size();
        } catch (Exception e)
        {
            return 0;
        }
    }

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
            return 0;
        }
    }

//    @GET
//    @Path("/storage")
//    @Produces(MediaType.APPLICATION_JSON)
////    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @TransactionAttribute(TransactionAttributeType.NEVER)
//    public StorageDTO getStrorage(@QueryParam("ipArticleTypeInStorage") String pStorageId)
//    {
//        try
//        {
//            ArticleTypeDTO result = this.myArticleBean.getArticleTypeByID(Integer.parseInt(pId));
////            ArticleTypeDTO result = new ArticleTypeDTO();
////            result.setId(1);
////            result.setName("Test Artikel");
////            result.setDescription("Test Beschreibung des Artikels");
////            result.setMinimumStock(12);
//            return result;
//        } catch (Exception e)
//        {
//            return new ArticleTypeDTO();
//        }
//    }
    @GET
    @Path("/test")
//    @Produces("application/xml")
//    @Consumes("application/xml")
    public String test()
    {
        return "Test OK";
    }

    @GET
    @Path("/storage")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public ArticleTypeExtended getStorage(@QueryParam("ipIdSimpleSearch") String pId)
    {        
//            int storageId = Integer.parseInt(pId);
//        List<Integer> occupiedYards = new ArrayList<Integer>();
//        List<Integer> freeYards = new ArrayList<Integer>();
//        List<YardDTO> yards = this.myPlaceBean.getAllYards(storageId);
//        List<ArticleTypeDTO> articleTypes = this.myArticleBean.getAllArticleTypes(storageId);
//        for (ArticleTypeDTO articleType : articleTypes)
//        {
//            List<ArticleDTO> articles = this.myArticleBean.getAllArticleByArticleType(articleType.getId());
//            for (ArticleDTO article : articles)
//            {
//                if (article.getYardID() != 0)
//                {
//                    occupiedYards.add(article.getYardID());
//                    modelOccupied.addRow(new Object[]
//                    {
//                        article.getYardID(), articleType.getName(),
//                        articleType.getId(), article.getId()
//                    });
//                }
//            }
//        }
//
//
//        for (YardDTO yard : yards)
//        {
//            boolean inOccupied = false;
//            for (Integer occupiedYard : occupiedYards)
//            {
//                if (occupiedYard == yard.getId())
//                {
//                    inOccupied = true;
//                    break;
//                }
//            }
//
//            if (inOccupied == false)
//            {
//                freeYards.add(yard.getId());
//            }
//        }
//
//        for (Integer yard : freeYards)
//        {
//            modelFree.addRow(new Object[]
//            {
//                yard
//            });
//        }
//    }
//
//    jTableStockManagerOverviewFreeYards.setModel (modelFree);
//
//    jTableStockManagerOverviewOccupiedYards.setModel (modelOccupied);
//    TableColumnAdjuster tca = new TableColumnAdjuster(jTableStockManagerOverviewFreeYards);
//
//    tca.adjustColumns ();
//    TableColumnAdjuster tca2 = new TableColumnAdjuster(jTableStockManagerOverviewOccupiedYards);
//
//    tca2.adjustColumns ();

return null;
    }


}

@XmlRootElement
class ArticleTypeExtended implements Serializable
{

    private int id;
    private String name;
    private String description;
    private int minimumStock;
    private int storageId;
    private String storageName;
    private int currentStock;
    private List<MovementDTO> movements;

    /**
     * Get the value of movements
     *
     * @return the value of movements
     */
    public List<MovementDTO> getMovements()
    {
        return movements;
    }

    /**
     * Set the value of movements
     *
     * @param movements new value of movements
     */
    public void setMovements(List<MovementDTO> movements)
    {
        this.movements = movements;
    }

    /**
     * Get the value of currentStock
     *
     * @return the value of currentStock
     */
    public int getCurrentStock()
    {
        return currentStock;
    }

    /**
     * Set the value of currentStock
     *
     * @param currentStock new value of currentStock
     */
    public void setCurrentStock(int currentStock)
    {
        this.currentStock = currentStock;
    }

    /**
     * Get the value of storageName
     *
     * @return the value of storageName
     */
    public String getStorageName()
    {
        return storageName;
    }

    /**
     * Set the value of storageName
     *
     * @param storageName new value of storageName
     */
    public void setStorageName(String storageName)
    {
        this.storageName = storageName;
    }

    /**
     * Get the value of storageId
     *
     * @return the value of storageId
     */
    public int getStorageId()
    {
        return storageId;
    }

    /**
     * Set the value of storageId
     *
     * @param storageId new value of storageId
     */
    public void setStorageId(int storageId)
    {
        this.storageId = storageId;
    }

    /**
     * Get the value of minimumStock
     *
     * @return the value of minimumStock
     */
    public int getMinimumStock()
    {
        return minimumStock;
    }

    /**
     * Set the value of minimumStock
     *
     * @param minimumStock new value of minimumStock
     */
    public void setMinimumStock(int minimumStock)
    {
        this.minimumStock = minimumStock;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id)
    {
        this.id = id;
    }
}