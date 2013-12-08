package de.hscoburg.etif.vbis.lagerix.backend.service;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.hscoburg.etif.vbis.lagerix.backend.service.dto.JsonResponseDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
//import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
//import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
//import de.hscoburg.etif.vbis.lagerix.backend.dao.UserBean;
//import de.hscoburg.etif.vbis.lagerix.backend.dto.UserDTO;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN)
@Stateless
public class UserManagementService {

    @EJB
    private UserManagerEJBRemoteInterface userBean;

    @GET
    @Path("ping")
    public String ping() {
        return "alive";
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseDTO login(@FormParam("email") String email, @FormParam("password") String password,
            @Context HttpServletRequest req) {

        JsonResponseDTO json = new JsonResponseDTO();

        //only login if not already logged in...
        if (req.getUserPrincipal() == null) {
            try {
                req.login(email, password);
                req.getServletContext().log("Authentication Demo: successfully logged in " + email);
            } catch (ServletException e) {
                e.printStackTrace();
                json.setStatus("FAILED");
                json.setErrorMsg("Authentication failed");
                return json;
            }
        } else {
            req.getServletContext().log("Skip logged because already logged in: " + email);
        }

        //read the user data from db and return to caller
        json.setStatus("SUCCESS");

        UserDTO user = userBean.find(email);
        req.getServletContext().log("Authentication Demo: successfully retrieved User Profile from DB for " + email);
        json.setData(user);

        //we don't want to send the hashed password out in the json response
        //userBean.detach(user);
        //user.setPassword(null);
        //user.setGroups(null);
        return json;
    }

    @GET
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseDTO logout(@Context HttpServletRequest req) {

        JsonResponseDTO json = new JsonResponseDTO();

        try {
            req.logout();
            json.setStatus("SUCCESS");
            req.getSession().invalidate();
        } catch (ServletException e) {
            e.printStackTrace();
            json.setStatus("FAILED");
            json.setErrorMsg("Logout failed on backend");
        }
        return json;
    }

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public JsonResponseDTO register(UserDTO newUser, @Context HttpServletRequest req) {

        JsonResponseDTO json = new JsonResponseDTO();
        json.setData(newUser); //just return the date we received

        //do some validation (in reality you would do some more validation...)
        //by the way: i did not choose to use bean validation (JSR 303)
        if (newUser.getPassword1().length() == 0 || !newUser.getPassword1().equals(newUser.getPassword2())) {
            json.setErrorMsg("Both passwords have to be the same - typo?");
            json.setStatus("FAILED");
            return json;
        }

        //User user = new User(newUser);
        //List<Group> groups = new ArrayList<Group>();
        //groups.add(Group.ADMINISTRATOR);
        //groups.add(Group.USER);
        //groups.add(Group.DEFAULT);
        //user.setGroups(groups);
        //this could cause a runtime exception, i.e. in case the user already exists
        //such exceptions will be caught by our ExceptionMapper, i.e. javax.transaction.RollbackException
        userBean.register(newUser); // this would use the clients transaction which is committed after save() has finished
        req.getServletContext().log("successfully registered new user: '" + newUser.getEmail() + "':'" + newUser.getPassword1() + "'");

        req.getServletContext().log("execute login now: '" + newUser.getEmail() + "':'" + newUser.getPassword1() + "'");
        try {
            req.login(newUser.getEmail(), newUser.getPassword1());
            json.setStatus("SUCCESS");
        } catch (ServletException e) {
            e.printStackTrace();
            json.setErrorMsg("User Account created, but login failed. Please try again later.");
            json.setStatus("FAILED"); //maybe some other status? you can choose...
        }

        return json;
    }

}
