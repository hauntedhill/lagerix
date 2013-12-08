<%-- 
    Document   : lagerix
    Created on : 25.11.2013, 16:48:14
    Author     : Maraike
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lagerix</title>
        <script src="../js/jquery/jquery-1.10.2.min.js" type="text/javascript"></script> 
        <link rel="stylesheet" type="text/css" href="../css/lagerix-web-app.css">
        <link href="../css/bootstrap.css" rel="stylesheet" media="screen">
        <script src="../js/bootstrap.js" type="text/javascript"></script>     
        <script src="../js/lagerix-web-app.js" type="text/javascript"></script> 
    </head>
    <body>
        <div class="container">
            <div class="page-header" style="margin: 10px 0px">
                <img src="../images/lagerix-logo.png" alt="Lagerix">
                <form><button type="submit" class="btn btn-default btn-sm" id="btnLogOut"><span class="glyphicon glyphicon-log-out" style="margin: 0px 15px 0px 0px"></span>Abmelden</button></form>
            </div>
            <div class="row">
                <div class="col-md-6">               
                    <div class="panel panel-default">
                        <div class="panel-heading">                          
                            <h3 class="panel-title" id="panelTitelArticleTypeDescription"><span class="glyphicon glyphicon-info-sign" style="margin: 0px 15px 0px 0px"></span>Informationen zu Artikelart </h3>                     
                        </div>
                        <div class="panel-body" >
                            <form class="form-horizontal" id="formArticleTypeDescription" name="formArticleTypeDescription" action="https://<%=request.getServerName()%>:<%=request.getLocalPort()%>/lagerix/services/secure/webApp/" autocomplete="off">
                                <div class="row" >
                                    <label class="control-label col-sm-3 "  for="ipArticleTypeId">Artikel-ID</label>  
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control input-sm" disabled id="ipArticleTypeId" name="ipArticleTypeId"> </div>
                                </div>
                                <div class="row">
                                    <label for="ipName" class="col-sm-3 control-label" >Name</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control input-sm " disabled id="ipName" name="ipName">
                                    </div>
                                </div>
                                <div class="row">
                                    <label for="textareaDescription" class="col-sm-3 control-label">Beschreibung</label>
                                    <div class="col-sm-9">
                                        <textarea rows="3" class="form-control input-sm " disabled id="textareaDescription" name="textareaDescription"></textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <label for="ipMinimumStock" class="col-sm-3 control-label">Mindestbestand</label>
                                    <div class="col-sm-2">
                                        <input type="number" class="form-control input-sm " disabled id="ipMinimumStock" name="ipMinimumStock">
                                    </div>
                                    <div class="col-sm-3" style="padding: 0px 2px 0px 0px; width: auto">
                                        <button class="btn btn-default btn-sm" id="btnChangeMinimumStock" name="btnChangeMinimumStock"><span class="glyphicon glyphicon-pencil" style="margin: 0px 5px 0px 0px"></span>Ändern</button>        
                                    </div>
                                    <div class="col-sm-2" style="padding: 0px; width: auto">
                                        <button class="btn btn-default btn-sm" id="btnChangeMinimumStockConfirm" name="btnChangeMinimumStockConfirm" style="visibility: hidden"><span class="glyphicon glyphicon-floppy-disk" style="margin: 0px 5px 0px 0px"></span>Speichern</button>         
                                    </div>
                                    <div class="col-sm-2"  style="padding: 0px; width: auto">
                                        <button class="btn btn-default btn-sm" id="btnChangeMinimumStockAbort" name="btnChangeMinimumStockAbort" style="visibility: hidden"><span class="glyphicon glyphicon-remove" style="margin: 0px 5px 0px 0px"></span>Abbrechen</button>         
                                    </div>                        
                                </div> 
                                <div class="row">
                                    <label for="ipCurrentStock" class="col-sm-3 control-label">Bestand</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control input-sm " disabled id="ipCurrentStock" name="ipCurrentStock">
                                    </div>                        
                                </div> 
                                <div class="row">
                                    <label for="stockTrend" class="col-sm-3 control-label">Bestandsverlauf</label>                                  
                                    
                                    
                                    <div class="lagerix-scrollable lagerix-bodycontainer col-sm-9">
                                        <table class="table table-condensed table-striped table-bordered table-hover">
                                            <thead>
                                                <tr><th>Zeitpunkt</th><th>Buchungsrichtung</th></tr>
                                            </thead>
                                            <tbody id="tbodyStockTrend">
                                            </tbody>
                                        </table>  
                                    </div>
                                    
                                    
                                    
                                </div>  
                                <hr>
                                <div class="row">
                                    <label for="ipStorageName" class="col-sm-3 control-label">Artikel in Lager</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control input-sm " disabled id="ipStorageName" name="ipStorageName">
                                    </div>      
                                    <label for="ipStorageId" class="col-sm-2 control-label">Lager-ID</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control input-sm " disabled id="ipStorageId" name="ipStorageId">
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
                                    <form class="form-horizontal" id="simpleSearchForm" name="simpleSearchForm" method="get" action="http://<%=request.getServerName()%>:<%=request.getLocalPort()%>/lagerix/services/webApp/simplesearch" autocomplete="off">
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
                                    <form class="form-horizontal" id="advancedSearchForm" name="advancedSearchForm"  method="get" action="http://<%=request.getServerName()%>:<%=request.getLocalPort()%>/lagerix/services/webApp/advancedsearch" autocomplete="off">
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
                                    <div class="lagerix-scrollable lagerix-bodycontainer">
                                        <table class="table table-condensed table-striped table-bordered table-hover">
                                            <thead>
                                                <tr><th>ID</th><th>Name</th><th>Beschreibung</th><th>Min-Best.</th></tr>
                                            </thead>
                                            <tbody id="tbodyAdvancedSearch">
                                            </tbody>
                                        </table>  
                                    </div>
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
                                    <form id="formRefreshMinUnderrun" name="formRefreshMinUnderrun" action="http://<%=request.getServerName()%>:<%=request.getLocalPort()%>/lagerix/services/webApp/underrunminstocks" method="get" autocomplete="off">
                                        <table>
                                            <tr>
                                                <td>Folgende in den Lagern befindliche Artikelarten haben den festgelegten Mindestbestand unterschritten:</td>
                                                <td><button type="submit" class="btn btn-default btn-sm" id="btnRefreshMinUnderrun"><span class="glyphicon glyphicon-refresh" style="margin: 0px 0px 10px 0px"></span><br/>Aktualisieren</button>
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                    <hr>  
                                    <div class="lagerix-scrollable lagerix-bodycontainer">
                                        <table class="table table-condensed table-striped table-bordered table-hover tablesorter" id="tableUnderrunMinStock">
                                            <thead>
                                                <tr><th>ID</th><th>Name</th><th>Beschreibung</th><th>Min-Best.</th></tr>
                                            </thead>
                                            <tbody id="tbodyUnderrunMinStock">                                   
                                            </tbody>
                                        </table>                                     
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
    </body>
</html>