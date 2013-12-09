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
import de.hscoburg.etif.vbis.lagerix.backend.service.dto.StorageOverviewDTO;
import de.hscoburg.etif.vbis.lagerix.backend.service.dto.YardInfoDTO;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
    
    @Path("saveEntry")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public int saveBookEntry(@FormParam("articleID") int articleID, @FormParam("locationID") int locationID, @FormParam("bookedIn") boolean bookedIn, @FormParam("timestamp") long timestamp) {
        
        System.out.println("Method saveBookEntry() called - Received values:");
        System.out.println("ArticleID: "+articleID);
        System.out.println("LocationID: "+locationID);
        System.out.println("BookedIn: "+bookedIn);
        System.out.println("Timestamp: "+timestamp);
        
        MovementDTO movementEntry = new MovementDTO();
        movementEntry.setArticleID(articleID);
        movementEntry.setBookedIn(bookedIn);
        movementEntry.setTimestamp(timestamp);
        
        return articleManager.saveMovementEntry(movementEntry, locationID);

    }
    
    @GET
    @Path("articleType/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArticleTypeDTO simpleSearch(@PathParam("id") int pId)
    {
        return articleManager.getArticleTypeByID(pId);
    }
    
    @Path("storageLocations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<YardDTO> storageLocationsForArticleType(@QueryParam("articleTypeId") int articleTypeId)
    {
        return placeManager.getYardsForArticleType(articleTypeId);
    }
    
    @Path("storageOverview")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StorageOverviewDTO storageOverview() {
        
        StorageDTO storage = placeManager.getStorages().get(0);
        List<YardDTO> yards = placeManager.getAllYards(storage.getId());
        List<ArticleTypeDTO> articleTypes = articleManager.getAllArticleTypes(storage.getId());
        List<Integer> occupiedYards = new LinkedList<Integer>();
        List<Integer> freeYards = new LinkedList<Integer>();
        
        for(ArticleTypeDTO articleType : articleTypes)
        {
            List<ArticleDTO> articles = articleManager.getAllArticleByArticleType(articleType.getId());
            for(ArticleDTO article : articles)
            {
                if(article.getYardID() != 0) {
                    occupiedYards.add(article.getYardID());
                    System.out.println("Occupied Yard: "+article.getYardID());
                }
            }
        }
        
        for(YardDTO y : yards) {
            if(!occupiedYards.contains(y.getId())) {
                freeYards.add(y.getId());
                System.out.println("Free Yard: "+y.getId());
            }
        }
        
        List<YardInfoDTO> yardList = new LinkedList<YardInfoDTO>();
        for(Integer i : occupiedYards) {
            YardInfoDTO yard = new YardInfoDTO();
            yard.setYardId(i);
            yard.setYardStatus("Belegt");
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
        StorageOverviewDTO result = new StorageOverviewDTO();
        result.setStorageInfo(yardList);
        return result;
        
        
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


//class StorageOverviewDTO {
//    
//    private List<YardInfoDTO> storageInfo;
//    
//    /**
//     * @return the storageInfo
//     */
//    public List<YardInfoDTO> getStorageInfo() {
//        return storageInfo;
//    }
//
//    /**
//     * @param storageInfo the storageInfo to set
//     */
//    public void setStorageInfo(List<YardInfoDTO> storageInfo) {
//        this.storageInfo = storageInfo;
//    }
//}
//
//class YardInfoDTO {
//    
//    private Integer yardId;
//    private String yardStatus;
//
//    /**
//     * @return the yardId
//     */
//    public Integer getYardId() {
//        return yardId;
//    }
//
//    /**
//     * @param yardId the yardId to set
//     */
//    public void setYardId(Integer yardId) {
//        this.yardId = yardId;
//    }
//
//    /**
//     * @return the yardStatus
//     */
//    public String getYardStatus() {
//        return yardStatus;
//    }
//
//    /**
//     * @param yardStatus the yardStatus to set
//     */
//    public void setYardStatus(String yardStatus) {
//        this.yardStatus = yardStatus;
//    }
//    
//}
