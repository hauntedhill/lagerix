/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Felix
 */
@Path("/secure/android/")
@Produces(MediaType.TEXT_PLAIN)
@Stateless
public class AndroidBookingService {
    
    @EJB
    ArticleManagerEJBRemoteInterface articleManager;
    
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
    
}
