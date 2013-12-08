package de.hscoburg.etif.vbis.lagerix.backend.service;

import de.hscoburg.etif.vbis.lagerix.backend.service.dto.JsonResponseDTO;
import java.util.Date;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/secure/timestamp")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class TimeService {

    @GET
    @Path("now")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseDTO getCurrentDate(@Context HttpServletRequest req) {

        JsonResponseDTO json = new JsonResponseDTO("SUCCESS");
        json.setData(new Date());
        return json;
    }

}
