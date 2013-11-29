<%-- 
    Document   : lagerix
    Created on : 25.11.2013, 16:48:14
    Author     : Maraike
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <script src="js/jquery/jquery-1.10.2.min.js"></script> 
        <link rel="stylesheet" type="text/css" href="css/lagerix-web-app.css">
        <link href="css/bootstrap.css" rel="stylesheet" media="screen">
        <script src="js/bootstrap.js"></script>
        <script src="js/json2.js"></script>         
        <script src="js/lagerix-web-app.js"></script> 
    </head>
    <body>
        <div class="container">
            <div class="page-header" style="margin: 10px 0px">
                <img src="images/lagerix-logo.png" alt="Lagerix">
                <form><button type="submit" class="btn btn-default btn-sm" id="btnLogOut"><span class="glyphicon glyphicon-log-out" style="margin: 0px 15px 0px 0px"></span>Abmelden</button></form>
            </div>
            <div class="row">
                <div class="col-md-6">               
                    <div class="panel panel-default">
                        <div class="panel-heading">                          
                            <h3 class="panel-title" id="panelTitelArticleTypeDescription"><span class="glyphicon glyphicon-info-sign" style="margin: 0px 15px 0px 0px"></span>Informationen zu Artikelart </h3>                     
                        </div>
                        <div class="panel-body" >
                            <form class="form-horizontal" id="formArticleTypeDescription" name="formArticleTypeDescription">
                                <div class="row" >
                                    <label class="control-label col-sm-3 "  for="ipArticleTypeIdArticleTypeDescription">Artikel-ID</label>  
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control input-sm" disabled id="ipArticleTypeIdArticleTypeDescription" name="ipArticleTypeIdArticleTypeDescription"> </div>
                                </div>
                                <div class="row">
                                    <label for="ipNameArticleTypeDescription" class="col-sm-3 control-label" >Name</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control input-sm " disabled id="ipNameArticleTypeDescription" name="ipNameArticleTypeDescription">
                                    </div>
                                </div>
                                <div class="row">
                                    <label for="textareaNoteArticleTypeDescription" class="col-sm-3 control-label">Beschreibung</label>
                                    <div class="col-sm-9">
                                        <textarea rows="3" class="form-control input-sm " disabled id="textareaNoteArticleTypeDescription" name="textareaNoteArticleTypeDescription"></textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <label for="ipMinimumStockArticleTypeDescription" class="col-sm-3 control-label">Mindestbestand</label>
                                    <div class="col-sm-6">
                                        <input type="number" class="form-control input-sm " disabled id="ipMinimumStockArticleTypeDescription" name="ipMinimumStockArticleTypeDescription">
                                    </div>
                                    <div class="col-sm-3" style="padding: 0px 5px 0px 0px; width: auto">
                                        <button type="submit" class="btn btn-default btn-sm" id="btnChangeMinimumStock" ><span class="glyphicon glyphicon-pencil" style="margin: 0px 10px 0px 0px"></span>Ändern</button>         
                                    </div>                        
                                </div> 
                                <div class="row">
                                    <label for="stockTrend" class="col-sm-3 control-label">Bestandsverlauf</label>
                                    <div class="col-sm-9">
                                        <textarea rows="4" class="form-control input-sm " disabled id="stockTrend" name="stockTrend"></textarea>
                                    </div>                        
                                </div>  
                                <hr>
                                <div class="row">
                                    <label for="ipArticleTypeInStorage" class="col-sm-3 control-label">Artikel in Lager</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control input-sm " disabled id="ipArticleTypeInStorage" name="ipArticleTypeInStorage">

                                    </div>                        
                                </div> 
                                <div class="row" style="padding: 10px">
                                    <div class="panel-group" id="accordionStorageOccupansy">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordionStorageOccupansy" href="#collapseStorageOccupancyOne">
                                                        Lagerbelegung anzeigen
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseStorageOccupancyOne" class="panel-collapse collapse ">
                                                <div class="panel-body">
                                                    Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung Lagerbelegung 
                                                </div>
                                            </div>
                                        </div>  
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel-group" id="accordion">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                        <span class="glyphicon glyphicon-search" style="margin: 0px 15px 0px 0px"></span>Einfache Suche
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseOne" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <form class="form-horizontal" id="simpleSearchForm" name="simpleSearchForm" method="get" action="http://<%=request.getServerName()%>:<%=request.getLocalPort()%>/lagerix/services/webApp/simplesearch">
                                        <div class="row" >
                                            <label class="control-label col-sm-3 " for="ipIdSimpleSearch">Artikelart-ID</label>  
                                            <div class="col-sm-5">
                                                <input type="text" class="form-control input-sm" id="ipIdSimpleSearch" name="ipIdSimpleSearch" placeholder="bitte ID eingeben"> </div>     
                                            <div class="col-sm-2" style="padding: 0px 5px 0px 0px; width: auto">
                                                <button type="submit" class="btn btn-default btn-sm" id="btnSearchSimpleSearch" >Suchen</button>         
                                            </div>
                                            <div class="col-sm-2" style="padding: 0px 5px 0px 0px; width: auto">
                                                <button type="reset" class="btn btn-default btn-sm" id="btnResetSimpleSearch" >Reset</button>   
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                        <span class="glyphicon glyphicon-zoom-in" style="margin: 0px 15px 0px 0px"></span>Erweiterte Suche
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseTwo" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <form class="form-horizontal" id="advancedSearchForm" name="advancedSearchForm"  method="get" action="http://<%=request.getServerName()%>:<%=request.getLocalPort()%>/lagerix/services/webApp/advancedsearch">
                                        <div class="row">
                                            <label for="ipNameAdvancedSearch" class="col-sm-3 control-label" >Name</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control input-sm " id="ipNameAdvancedSearch" name="ipNameAdvancedSearch" placeholder="">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="ipNoteAdvancedSearch" class="col-sm-3 control-label">Beschreibung</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control input-sm " id="ipNoteAdvancedSearch" name="ipNoteAdvancedSearch" placeholder="">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <label for="ipMinimumStockAdvancedSearch" class="col-sm-3 control-label">Mindestbestand</label>
                                            <div class="col-sm-5">
                                                <input type="number" class="form-control input-sm " id="ipMinimumStockAdvancedSearch" name="ipMinimumStockAdvancedSearch">
                                            </div>                      
                                            <div class="col-sm-2" style="padding: 0px 5px 0px 0px; width: auto">
                                                <button type="submit" class="btn btn-default btn-sm" id="btnSearchAdvancedSearch" >Suchen</button>         
                                            </div>
                                            <div class="col-sm-2" style="padding: 0px 5px 0px 0px; width: auto">
                                                <button type="reset" class="btn btn-default btn-sm" id="btnResetAdvancedSearch" >Reset</button>   
                                            </div>
                                        </div>
                                    </form>
                                    <br>
                                    <!--Suchergebnisse-->
                                    <h4>Suchergebnisse</h4>
                                    <hr>
                                    <table class="table table-condensed table-striped table-bordered table-hover">
                                        <thead>
                                            <tr><th>ID</th><th>Name</th><th>Min-Best.</th><th>Beschreibung</th></tr>
                                        </thead>
                                        <tbody id="tbodyAdvancedSearch">
                                            <tr></tr>
                                        </tbody>
                                    </table>                                   
                                </div>
                            </div>
                        </div>
                        <!--Articles with underrun miniumum stock-->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                        <span class="glyphicon glyphicon-exclamation-sign" style="margin: 0px 15px 0px 0px"></span> Artikelarten mit unterschrittenem Mindestbestand
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseThree" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <form id="formRefreshMinUnderrun" name="formRefreshMinUnderrun" action="http://<%=request.getServerName()%>:<%=request.getLocalPort()%>/lagerix/services/webApp/underrunminstocks" method="get">
                                        <table>
                                            <tr>
                                                <td>Folgende in den Lagern befindliche Artikelarten haben den festgelegten Mindestbestand unterschritten:</td>
                                                <td><button type="submit" class="btn btn-default btn-sm" id="btnRefreshMinUnderrun"><span class="glyphicon glyphicon-refresh" style="margin: 0px 0px 10px 0px"></span><br/>Aktualisieren</button>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                    <hr>                          
                                    <table class="table table-condensed table-striped table-bordered table-hover tablesorter" id="tableUnderrunMinStock">
                                        <thead>
                                            <tr><th>ID</th><th>Name</th><th>Beschreibung</th><th>Min-Best.</th></tr>
                                        </thead>
                                        <tbody id="tbodyUnderrunMinStock">
                                            <tr></tr>
                                        </tbody>
                                    </table>                                     
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>       
    </body>
</html>