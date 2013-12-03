<%-- 
    Document   : articleTest.jsp
    Created on : 28.11.2013, 13:59:16
    Author     : Haunted
--%>

<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO"%>
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
    <% 
    
    
    try {
        
        request.login("test@test.de", "test");
InitialContext ctx =new InitialContext();
/* Nachfolgender JNDI-Name hat funktioniert
bei Start des Clients in NetBeans 7.0.1 */
String
ejb_jndi_name
="java:global/LagerixPrj-1.0.0/LagerixEJBModule-1.0.0/PlaceManagerEJBean!de.hscoburg.etif.vbis.lagerix.backend.interfaces.PlaceManagerEJBRemoteInterface";
PlaceManagerEJBRemoteInterface bean =
(PlaceManagerEJBRemoteInterface) ctx.lookup(ejb_jndi_name);



 

 StorageDTO s = bean.createNewStorage("test");
 out.print("Create new Storage:"+ s+"</br></br>");
 
 YardDTO y = bean.createNewYard(s.getId());
 out.print("Create new Yard:"+ y +"</br></br>");
 
  out.print("get All yards for storage"+ bean.getAllYardsForStorage(s.getId()) +"</br></br>");
 
 out.print("get All storages"+ bean.getAllStorages() +"</br></br>");
 
 out.print("get All yards"+ bean.getAllYards(s.getId()) +"</br></br>");
 
 out.print("get locatopm for articleType"+ bean.getYardsForArticleType(1) +"</br></br>");
 
 out.print("get storage"+ bean.getStorages() +"</br></br>");
 
 bean.deleteYard(y.getId());
 out.print("delte yard</br></br>");
 
 bean.deleteStorage(s.getId());
 out.print("delete storage</br></br>");
 
} catch (Exception e) { System.out.println(e);
}
    
    
    %>
