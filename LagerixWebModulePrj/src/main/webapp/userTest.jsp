<%-- 
    Document   : articleTest.jsp
    Created on : 28.11.2013, 13:59:16
    Author     : Haunted
--%>

<%@page import="java.util.Arrays"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.*"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.*"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
    <% 
    
    
    try {
InitialContext ctx =new InitialContext();
/* Nachfolgender JNDI-Name hat funktioniert
bei Start des Clients in NetBeans 7.0.1 */
String
ejb_jndi_name
="java:global/LagerixPrj-1.0.0/LagerixEJBModule-1.0.0/UserManagerEJBean!de.hscoburg.etif.vbis.lagerix.backend.interfaces.UserManagerEJBRemoteInterface";
UserManagerEJBRemoteInterface bean =
(UserManagerEJBRemoteInterface) ctx.lookup(ejb_jndi_name);

request.login("test@test.de", "test");
 

UserDTO u = new UserDTO();
u.setEmail("test@test.de");

GroupDTO g1 = new GroupDTO();
g1.setGroup(GroupType.ADMINISTRATOR);
g1.setGroup(GroupType.BENUTZER);
g1.setStorageId(Arrays.asList(new Integer[] {1,801}));

u.setGroups(Arrays.asList(new GroupDTO[] {g1}));

 bean.editUserGroups(u);

 
} catch (Exception e) { System.out.println(e);
}
    
    
    %>
