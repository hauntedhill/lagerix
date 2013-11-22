/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.service;

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
@Path("/book")
@Produces(MediaType.TEXT_PLAIN)
@Stateless
public class BookingService {
    
    
    @Path("ping")
    @GET
    public String ping() {
        return "alive";
    }
    
    @Path("saveEntry")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public int saveBookEntry(@FormParam("articleID") int articleID, @FormParam("locationID") int locationID, @FormParam("bookedIn") boolean bookedIn, @FormParam("userID") int userID, @FormParam("timestamp") long timestamp) {
        System.out.println("Received values:");
        System.out.println("ArticleID: "+articleID);
        System.out.println("LocationID: "+locationID);
        System.out.println("BookedIn: "+bookedIn);
        System.out.println("UserID: "+userID);
        System.out.println("Timestamp: "+timestamp);
        return 0;

    }
    
}
