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
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.*;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import javax.ejb.EJB;
import java.util.List;

/**
 * REST Web Service
 *
 * @author Tamas
 */
@Path("webApp")
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
    public ArticleTypeDTO simpleSearch(@QueryParam("ipIdSimpleSearch") String pId)
    {
        try
        {
            int id = Integer.parseInt(pId);
            ArticleTypeDTO result = this.myArticleBean.getArticleTypeByID(id);
            return result;
        }
        catch (Exception e)
        {
            return new ArticleTypeDTO();
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
        }
        catch (Exception e)
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
        }
        catch (Exception e)
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
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    @POST
    @Path("/minimumstock")
    @Produces(MediaType.TEXT_PLAIN)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public int changeMinimumStock(@QueryParam("id") String pId,  @QueryParam("ipMinimumStock") String pMinStock)
    {
        try
        {
            int id = Integer.parseInt(pId);
            int minStock = Integer.parseInt(pMinStock);
            int result = this.myArticleBean.updateMinimumStock(id, minStock);
            return result;
        }
        catch (Exception e)
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
}
