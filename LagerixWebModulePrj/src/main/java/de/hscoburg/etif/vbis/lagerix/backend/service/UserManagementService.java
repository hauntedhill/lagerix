package de.hscoburg.etif.vbis.lagerix.backend.service;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
//import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
//import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
//import de.hscoburg.etif.vbis.lagerix.backend.dao.UserBean;
//import de.hscoburg.etif.vbis.lagerix.backend.dto.UserDTO;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN)
@Stateless
public class UserManagementService
{

    @EJB
    private UserManagerEJBRemoteInterface userBean;

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@FormParam("email") String email, @FormParam("password") String password,
            @Context HttpServletRequest req)
    {

        //only login if not already logged in...
        if (req.getUserPrincipal() == null)
        {
            try
            {
                req.login(email, password);
                req.getServletContext().log("Authentication Demo: successfully logged in " + email);
            } catch (ServletException e)
            {
                e.printStackTrace();
                return "FAILED";
            }
        } else
        {
            req.getServletContext().log("Skip logged because already logged in: " + email);
        }

        req.getServletContext().log("Authentication Demo: successfully retrieved User Profile from DB for " + email);

        return "SUCCESS";
    }
    
    /**
     * Login method especially for lagerix web application. Ensures that only only user with role 'EINKAEUFER' can login.
     * @param email users email
     * @param password users password
     * @param req
     * @return Whether login was successful or not.
     */
    @POST
    @Path("loginwebapp")
    @Produces(MediaType.TEXT_PLAIN)
    public String loginWebApp(@FormParam("email") String email, @FormParam("password") String password,
            @Context HttpServletRequest req)
    {
        //only login if not already logged in...
        if (req.getUserPrincipal() == null)
        {
            try
            {
                req.login(email, password);
                req.getServletContext().log("Authentication Demo: successfully logged in " + email);
            } catch (ServletException e)
            {
                e.printStackTrace();
                return "FAILED";
            }
        } else
        {
            req.getServletContext().log("Skip logged because already logged in: " + email);
        }

        req.getServletContext().log("Authentication Demo: successfully retrieved User Profile from DB for " + email);
        //ensure that only EINKAEUFER role is logged in
        if (userBean.isInGroup(GroupType.EINKAEUFER))
        {
            return "SUCCESS";
        } else
        {
            logout(req);
            return "FAILED";
        }
    }

    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@Context HttpServletRequest req)
    {

        try
        {
            req.logout();
            req.getSession().invalidate();
        } catch (ServletException e)
        {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }
}
