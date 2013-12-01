<%-- 
    Document   : articleTest.jsp
    Created on : 28.11.2013, 13:59:16
    Author     : Haunted
--%>

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
="java:global/LagerixPrj-1.0.0/LagerixEJBModule-1.0.0/ArticleManagerEJBean!de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface";
ArticleManagerEJBRemoteInterface bean =
(ArticleManagerEJBRemoteInterface) ctx.lookup(ejb_jndi_name);


 MovementDTO m = new MovementDTO();
 m.setArticleID(1);
 m.setBookedIn(true);
 

 
 out.print("Moving in Artikel1:"+bean.saveMovementEntry(m,1)+"</br>");
 m.setBookedIn(false);
 out.print("Moving out Artikel1:"+bean.saveMovementEntry(m,1)+"</br>");
  out.print("</br></br>");
 out.print("update minimumStock ArticleType:"+bean.updateMinimumStock(1, 100) +"</br>");
 out.print("fecth ArticleType:"+bean.getArticleTypeByID(1).toString() +"</br>");
 out.print("update minimumStock ArticleType:"+bean.updateMinimumStock(1, 50) +"</br>");
 out.print("fecth ArticleType:"+bean.getArticleTypeByID(1).toString() +"</br>");
 
 out.print("createNewArticleType:"+bean.createNewArticleType("test", "test123",1).toString() +"</br>"); 
  out.print("</br></br>");
  out.print("get all articleTypes:"+bean.getAllArticleTypes(1).toString() +"</br>"); 
  out.print("</br></br>");
 out.print("createNewArticle:"+bean.createNewArticle(1).toString() +"</br>"); 
 
  out.print("</br></br>");
 out.print("searchArticleType:"+bean.searchArticleType("te", "", "").toString() +"</br>"); 
  out.print("</br></br>");
 out.print("searchArticleType:"+bean.searchArticleType("A", "", "").toString() +"</br>"); 
  out.print("</br></br>");
 out.print("searchArticleType:"+bean.searchArticleType("", "", "50").toString() +"</br>"); 
  out.print("</br></br>");
 out.print("searchArticleType:"+bean.searchArticleType("t", "test1", "0").toString() +"</br>"); 
  out.print("</br></br>");
 out.print("get Movements for ArticleTypes"+bean.getMovementEntriesForArticleType(1).toString() +"</br>"); 
 
 out.print("</br></br>");
 
 out.print("articleTypes under stock"+bean.getAllArticleTypesWithUnderrunMinStock().toString() +"</br>"); 
 
} catch (Exception e) { System.out.println(e);
}
    
    
    %>
