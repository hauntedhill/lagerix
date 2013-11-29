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
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.*;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
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
    ArticleManagerEJBRemoteInterface myBean;
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
    public ArticleTypeDTO simpleSearch(@QueryParam("ipIdSimpleSearch") String pId)//final ArticleId pArticleId)//, @Context HttpServletRequest req)
    {
        try
        {
            ArticleTypeDTO result = this.myBean.getArticleTypeByID(Integer.parseInt(pId));
            return result;
        } catch (Exception e)
        {
            return new ArticleTypeDTO();
        }
    }

    @GET
    @Path("/advancedsearch")
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<ArticleTypeDTO> advancedSearch(@QueryParam("ipNameAdvancedSearch") String pId, @QueryParam("ipNoteAdvancedSearch") String pNote, @QueryParam("ipMinimumStockAdvancedSearch") String pMinimumStock)
    {
        try
        {
            List<ArticleTypeDTO> result = new java.util.ArrayList<ArticleTypeDTO>();

            int id = 1;
            String name = "Artikelart ";
            String description = "Beschreibung Artikelart ";
            int minStock = 5;

            for (int i = 1; i < 11; i++)
            {
                result.add(this.myBean.getArticleTypeByID(i));
            }

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
            List<ArticleTypeDTO> result = new java.util.ArrayList<ArticleTypeDTO>();

            int id = 1;
            String name = "Artikelart ";
            String description = "Beschreibung Artikelart ";
            int minStock = 5;

            for (int i = 1; i < 11; i++)
            {
                result.add(this.myBean.getArticleTypeByID(i));
            }

            return result;
        } catch (Exception e)
        {
            return new java.util.ArrayList<ArticleTypeDTO>();
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
//            ArticleTypeDTO result = this.myBean.getArticleTypeByID(Integer.parseInt(pId));
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
