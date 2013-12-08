
<%@page import="de.hscoburg.etif.vbis.lagerix.backend.interfaces.ArticleManagerEJBRemoteInterface"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"
        %><%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' 
        %><c:if test="${pageContext.request.userPrincipal!=null}">
    <c:redirect url="/secure/lagerix.jsp"/>
    <!-- this will redirect if user is already logged in -->
</c:if>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="./css/auth.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lagerix Login</title>
        <script src="js/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
        <link  href="css/lagerix-web-app.css" rel="stylesheet" type="text/css">
        <link href="css/bootstrap.css" rel="stylesheet" media="screen">
        <script src="js/bootstrap.js" type="text/javascript"></script>     
        <script src="js/lagerix-web-app.js" type="text/javascript"></script> 

        <script type="text/javascript">
            $(function() {
                "use strict";
                $(document.forms['loginForm']).submit(function(event) {

                    var data = {
                        email: this.email.value,
                        password: this.password.value
                    };
                    var destinationUrl = this.action;

                    $.ajax({
                        url: destinationUrl,
                        type: "POST",
                        data: $("#loginForm").serialize(),
                        cache: false,
                        dataType: "json",
                        success: function(data, textStatus, jqXHR) {
                            //alert("success");
                            if (data.status == "SUCCESS") {
                                //redirect to secured page
                                window.location.replace("http://" + window.location.host + "<%=request.getContextPath()%>/secure/lagerix.jsp");
                            } else {
                                alert("Login failed!");
                            }
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            alert("error - HTTP STATUS: " + jqXHR.status);
                        },
                        complete: function(jqXHR, textStatus) {
                            //alert("complete");
                        }
                    });

                    //event.preventDefault();
                    return false;
                });
            });
        </script>

    </head>
    <body>
        <div class="container">
            <div class="page-header" style="margin: 10px 0px">
                <img src="images/lagerix-logo.png" alt="Lagerix">
            </div>
            <div class="row">
                <div class="col-md-4 col-md-offset-4"> 
                    <br><br><br>
                    <div class="panel panel-default">
                        <div class="panel-heading">                          
                            <h3 class="panel-title" id="panelTitelArticleTypeDescription"><span class="glyphicon glyphicon-log-in" style="margin: 0px 15px 0px 0px"></span>Login</h3>                     
                        </div>
                        <div class="panel-body" >
                            <form class="form-horizontal" id="loginForm" name="loginForm" action="<%=request.getContextPath()%>/services/auth/login" method="post">
                                <div class="row" >
                                    <label class="control-label col-sm-3 " for="email">Email</label> 
                                    <div class="col-sm-9">
                                        <input  class="form-control input-sm"  type="text" id="email" name="email"/>
                                    </div>
                                </div>
                                <div class="row" >
                                    <label class="control-label col-sm-3 " for="password">Password</label> 
                                    <div class="col-sm-9">
                                        <input  class="form-control input-sm"  type="password" id="password" name="password"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-2 col-sm-offset-5">
                                        <button type="submit" class="btn btn-default btn-sm">Login</button>
                                    </div>
                                </div>
                            </form> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>