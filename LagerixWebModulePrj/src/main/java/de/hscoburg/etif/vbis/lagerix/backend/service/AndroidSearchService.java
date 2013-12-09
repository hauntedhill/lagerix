/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Felix
 */
@Path("/secure/android/")
@Produces(MediaType.TEXT_PLAIN)
@Stateless
public class AndroidSearchService {
    
    @EJB
    ArticleManagerEJBRemoteInterface articleManager;
    
    @GET
    @Path("articleType/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArticleTypeDTO simpleSearch(@PathParam("id") int pId)
    {
        return articleManager.getArticleTypeByID(pId);
    }
    
    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ArticleTypeDTO> search(@QueryParam("name") String pName, @QueryParam("description") String pDescription)
    {
        try
        {
            List<ArticleTypeDTO> result = articleManager.searchArticleType(pName, pDescription, "");
            return result;
        } catch (Exception e)
        {
            return new java.util.ArrayList<ArticleTypeDTO>();
        }
    }
    
}
