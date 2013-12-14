/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import de.hscoburg.etif.vbis.lagerix.backend.service.dto.YardInfoDTO;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
public class AndroidService {
    
    @EJB
    ArticleManagerEJBRemoteInterface articleManager;
    
    @EJB
    PlaceManagerEJBRemoteInterface placeManager;
    
    
    /**
     * This method saves a bookEntry to a corresponding article and location ID in the database.
     * @param pArticleID ID of the article
     * @param pYardID ID of the yard
     * @param pBookedIn "true" if the article is booked in, "false" if the article is booked out
     * @param pTimestamp timestamp of the bookEntry
     * @return statuscode with one of the following values:
     *                      0: No error
     *                      1: Article id or yard id does not exist
     *                      2: Release not possible, because the article is not stored in the specified yard
     *                      3: Incorporation not possible, because the article is already stored in another yard
     *                      4: Incorporation not possible, because the yard is occupied
     */
    @Path("saveEntry")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public int saveBookEntry(@FormParam("articleID") int pArticleID, @FormParam("yardID") int pYardID, @FormParam("bookedIn") boolean pBookedIn, @FormParam("timestamp") long pTimestamp) 
    {
        
        System.out.println("Method saveBookEntry() called - Received values:");
        System.out.println("ArticleID: "+pArticleID);
        System.out.println("LocationID: "+pYardID);
        System.out.println("BookedIn: "+pBookedIn);
        System.out.println("Timestamp: "+pTimestamp);
        
        MovementDTO movementEntry = new MovementDTO();
        movementEntry.setArticleID(pArticleID);
        movementEntry.setBookedIn(pBookedIn);
        movementEntry.setTimestamp(pTimestamp);
        
        return articleManager.saveMovementEntry(movementEntry, pYardID);

    }
    
    /**
     * This method searches the database for name and description of an article type
     * @param pName name of the article type
     * @param pDescription description of the article type
     * @return list of article types, that match the search criterias
     */    
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
    
    /**
     * This methods returns a list of yards, where the article type with the specified ID is stored in
     * @param pArticleTypeId ID of the article type
     * @return list of yards, where articles of the specified article type are stored in
     */
    @Path("yards")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<YardDTO> yardsForArticleType(@QueryParam("articleTypeId") int pArticleTypeId)
    {
        return placeManager.getYardsForArticleType(pArticleTypeId);
    }
    
    /**
     * This method returns a list with the status of all yards from the storage, that is related to the logged in users
     * @return List of objects, that consist of the yard id and the corresponding status. If a yard is occupied, the status is equal to the name of the stored article type, else the status is "Frei".
     */
    @Path("storageOverview")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<YardInfoDTO> storageOverview() 
    {
        
        StorageDTO storage = placeManager.getStorages().get(0);
        List<YardDTO> yards = placeManager.getAllYards(storage.getId());
        List<ArticleTypeDTO> articleTypes = articleManager.getAllArticleTypes(storage.getId());
        Map<Integer, String> occupiedYards = new HashMap<Integer, String>();
        List<Integer> freeYards = new LinkedList<Integer>();
        
        for(ArticleTypeDTO articleType : articleTypes)
        {
            List<ArticleDTO> articles = articleManager.getAllArticleByArticleType(articleType.getId());
            for(ArticleDTO article : articles)
            {
                if(article.getYardID() != 0) {
                    occupiedYards.put(article.getYardID(), articleType.getName());
                    System.out.println("Occupied Yard: "+article.getYardID());
                }
            }
        }
        
        for(YardDTO y : yards) {
            if(!occupiedYards.containsKey(y.getId())) {
                freeYards.add(y.getId());
                System.out.println("Free Yard: "+y.getId());
            }
        }
        
        List<YardInfoDTO> yardList = new LinkedList<YardInfoDTO>();
        for(Integer key : occupiedYards.keySet()) {
            YardInfoDTO yard = new YardInfoDTO();
            yard.setYardId(key);
            yard.setYardStatus(occupiedYards.get(key));
            yardList.add(yard);
        }
        for(Integer i : freeYards) {
            YardInfoDTO yard = new YardInfoDTO();
            yard.setYardId(i);
            yard.setYardStatus("Frei");
            yardList.add(yard);
        }
        
        Collections.sort(yardList, new Comparator<YardInfoDTO>() {
            @Override
            public int compare(YardInfoDTO o1, YardInfoDTO o2) {
                return o1.getYardId().compareTo(o2.getYardId());
            }
        });
        return yardList;
        
        
    }
    
}
