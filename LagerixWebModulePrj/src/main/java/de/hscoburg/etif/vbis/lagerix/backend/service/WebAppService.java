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
            tc.setStorageName(this.myPlaceBean.getStorage(tc.getStorageId()).getName());
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
    public String test()
    {
        return "Test OK";
    }

    @GET
    @Path("/storage")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public StorageExtended getStorage(@QueryParam("storageId") String pId)
    {
        int storageId = Integer.parseInt(pId);
        StorageExtended storeEx = new StorageExtended();

        storeEx.setStorageId(storageId);
        StorageDTO store = this.myPlaceBean.getStorage(storageId);
        String name = store.getName();
        storeEx.setName(this.myPlaceBean.getStorage(storageId).getName());
        storeEx.setYards(new ArrayList<YardExtended>());

        List<YardDTO> yards = this.myPlaceBean.getAllYardsForStorage(storageId);
        List<ArticleTypeDTO> articleTypes = this.myArticleBean.getAllArticleTypes(storageId);

        for (YardDTO yard : yards)
        {
            YardExtended myYardEx = new YardExtended();
            myYardEx.setYardId(yard.getId());
            storeEx.getYards().add(myYardEx);
        }

        for (ArticleTypeDTO articleType : articleTypes)
        {
            List<ArticleDTO> articles = this.myArticleBean.getAllArticleByArticleType(articleType.getId());
            for (ArticleDTO article : articles)
            {
                if (article.getYardID() != 0)
                {
                    for (YardExtended yard : storeEx.getYards())
                    {
                        if (yard.getYardId() == article.getYardID())
                        {
                            yard.setArticleId(article.getId());
                            yard.setArticleTypeId(articleType.getId());
                            yard.setArticleTypeName(articleType.getName());
                            break;
                        }
                    }
                }
            }
        }
        return storeEx;
    }
}

class YardExtended implements Serializable
{

    private int yardId;
    private int articleId;
    private int articleTypeId;
    private String articleTypeName;

// <editor-fold defaultstate="collapsed" desc="getter and setter">    
    public int getYardId()
    {
        return yardId;
    }

    public void setYardId(int yardId)
    {
        this.yardId = yardId;
    }

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
//</editor-fold>
}

class StorageExtended implements Serializable
{

    private String name;
    private int storageId;
    private List<YardExtended> yards;

// <editor-fold defaultstate="collapsed" desc="getter and setter">     
    public List<YardExtended> getYards()
    {
        return yards;
    }

    public void setYards(List<YardExtended> yards)
    {
        this.yards = yards;
    }

    public int getStorageId()
    {
        return storageId;
    }

    public void setStorageId(int storageId)
    {
        this.storageId = storageId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
//</editor-fold>
}

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

// <editor-fold defaultstate="collapsed" desc="getter and setter">    
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
