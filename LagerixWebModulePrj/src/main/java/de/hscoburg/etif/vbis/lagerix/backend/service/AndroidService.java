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
     * Diese Methode speichert eine Ein- oder Ausbuchung zu einer Artikel- und Orts-ID in der Datenbank
     * @param pArticleID ID der Artikelart
     * @param pLocationID ID des Lagerplatzes
     * @param pBookedIn True, falls Artikel eingelagert wurde, sonst False
     * @param pTimestamp Zeitstempel der Buchung
     * @return Statuscode:
     *                      0: Kein Fehler
     *                      1: Artikel- oder Lagerplatz-ID existiert nicht
     *                      2: Ausbuchung nicht möglich, da Artikel nicht im entsprechend Lagerplatz liegt
     *                      3: Einbuchung nicht möglich, da Artikel bereits in einem anderen Lagerplatz liegt
     *                      4: Einbuchung nicht möglich, da Lagerplatz bereits belegt ist
     */
    @Path("saveEntry")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public int saveBookEntry(@FormParam("articleID") int pArticleID, @FormParam("locationID") int pLocationID, @FormParam("bookedIn") boolean pBookedIn, @FormParam("timestamp") long pTimestamp) {
        
        System.out.println("Method saveBookEntry() called - Received values:");
        System.out.println("ArticleID: "+pArticleID);
        System.out.println("LocationID: "+pLocationID);
        System.out.println("BookedIn: "+pBookedIn);
        System.out.println("Timestamp: "+pTimestamp);
        
        MovementDTO movementEntry = new MovementDTO();
        movementEntry.setArticleID(pArticleID);
        movementEntry.setBookedIn(pBookedIn);
        movementEntry.setTimestamp(pTimestamp);
        
        return articleManager.saveMovementEntry(movementEntry, pLocationID);

    }
    
    /**
     * Diese Methode liefert die Artikelart zu einer übergebenen ID zurück.
     * @param pId ID der Artikelart
     * @return Artikelart mit übergebener ID
     */
    @GET
    @Path("articleType/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArticleTypeDTO simpleSearch(@PathParam("id") int pId)
    {
        return articleManager.getArticleTypeByID(pId);
    }
    
    /**
     * Diese Methode durchsucht die Datenbank nach dem Namen und der Beschreibung einer Artikelart
     * @param pName Suchkriterium "Name der Artikelart"
     * @param pDescription Suchkriterium "Beschreibung der Artikelart"
     * @return Liste von Artikelarten, die mit den Suchkriterien übereinstimmen.
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
     * Diese Methode liefert eine Liste aller Lagerplätze zurück, in denen die Artikelart mit der übergebenen ID eingelagert ist.
     * @param pArticleTypeId ID der Artikelart
     * @return Liste mit Lagerplätzen, in denen die Artikelart mit der übergebenen ID eingelagert ist.
     */
    @Path("storageLocations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<YardDTO> storageLocationsForArticleType(@QueryParam("articleTypeId") int pArticleTypeId)
    {
        return placeManager.getYardsForArticleType(pArticleTypeId);
    }
    
    /**
     * Diese Methode liefert eine Liste mit dem Status aller Lagerplätze des Lagers zurück, das dem eingeloggten Benutzer zugeordnet ist.
     * @return Liste mit Objekten, die die ID und den Status eines Lagerplatzes beinhalten. Ist ein Lagerplatz belegt, entspricht der Status dem Namen der eingelagerten Artikelart, ansonsten ist „Frei“ eingetragen.
     */
    @Path("storageOverview")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<YardInfoDTO> storageOverview() {
        
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
