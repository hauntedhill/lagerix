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
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import com.google.gson.Gson;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import javax.ejb.EJB;

/**
 * REST Web Service
 *
 * @author Tamas
 */
@Path("webApp")
public class WebAppService
{

//    @EJB
//    ArticleManagerEJBRemoteInterface myBean;
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
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public String simpleSearch(@QueryParam("ipIdSimpleSearch") String pId)//final ArticleId pArticleId)//, @Context HttpServletRequest req)
    {
        int i = 5;
        try
        {
//            ArticleTypeDTO result = this.myBean.getArticleTypeByID(Integer.parseInt("1"));
            ArticleTypeDTO result = new ArticleTypeDTO();
            result.setId(1);
            result.setName("Test Artikel");
            result.setDescription("Test Beschreibung des Artikels");
            result.setMinimumStock(12);
            Gson gson = new Gson();
            String json = gson.toJson(result);
            return json;
        } catch (Exception e)
        {
            return "error";
        }
    }

    @GET
    @Path("/test")
//    @Produces("application/xml")
//    @Consumes("application/xml")
    public String test()
    {
        return "Test OK";
    }
}
