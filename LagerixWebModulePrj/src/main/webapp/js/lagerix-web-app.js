$(document).ready(function() {
    $(document.forms['advancedSearchForm']).submit(function() {
        var destinationUrl = this.action;

        $.ajax({
            url: destinationUrl,
            type: "GET",
            data: $("#advancedSearchForm").serialize(),
            cache: false,
            dataType: "json",
            success: function(data, textStatus, jqXHR) {
                $("#tbodyAdvancedSearch").html(displayArticleTypes(data, textStatus, jqXHR));
            }
        });
        return false;
    });
});



$(document).ready(function() {
    $(document.forms['formRefreshMinUnderrun']).submit(function() {
        var urli = this.action;
        $.ajax({
            url: urli,
            type: "GET",
            data: "",
            cache: false,
            dataType: "json",
            success: function(data, textStatus, jqXHR) {
                $("#tbodyUnderrunMinStock").html(displayArticleTypes(data));
            }
        });
        return false;
    });
});



function displayArticleTypes(data) {
    var rows = "";
    for (var i = 0; i < data.length; i++) {
        rows += "<tr><td>" + data[i].id + "</td><td>" + data[i].name + "</td><td>" + data[i].description + "</td><td>" + data[i].minimumStock + "</td></tr>";
    }
    return rows;
}






//-----------------------------------------------------------------------------------------------
//$(document).ready(function() {
//    $(document.forms['simpleSearchForm']).submit(function(event) {
//        var destinationUrl = this.action;
//
//        $.ajax({
//            url: destinationUrl,
//            type: "GET",
//            data: $("#simpleSearchForm").serialize(),
//            cache: false,
//            dataType: "json",
//            success: function(data, textStatus, jqXHR) {
//                displayArticleType(data, textStatus, jqXHR);
//            }
//        });
//        //event.preventDefault();
//        return false;
//    });
//});
//
//$(document).ready(function() {
//    $(document.forms['advancedSearchForm']).submit(function() {
//        var destinationUrl = this.action;
//
//        $.ajax({
//            url: destinationUrl,
//            type: "GET",
//            data: $("#advancedSearchForm").serialize(),
//            cache: false,
//            dataType: "json",
//            success: function(data, textStatus, jqXHR) {
//                $("#tbodyAdvancedSearch").html(displayArticleTypes(data, textStatus, jqXHR));
//            }
//        });
//        return false;
//    });
//});
//
//$(document).ready(function() {
//    $(document.forms['advancedSearchForm']).reset(function() {
// 
//            $("#tbodyAdvancedSearch").html("");
//
//    });
//});
//
//
//$(document).ready(function() {
//    $(document.forms['formRefreshMinUnderrun']).submit(function() {
//        getArticleTypesWithUnderrunMinStock();
//        return false;
//    });
//});
//
//function getArticleTypesWithUnderrunMinStock() {
//    var urli = $(document.forms['formRefreshMinUnderrun']).action;
//    $.ajax({
//        url: urli,
//        type: "GET",
//        data: "",
//        cache: false,
//        dataType: "json",
//        success: function(data, textStatus, jqXHR) {
//            $("#tbodyUnderrunMinStock").html(displayArticleTypes(data));
//        }
//    });
//    return false;
//}
//
//function displayArticleType(data, textStatus, jqXHR) {
//    $("#ipArticleTypeIdArticleTypeDescription").val(data.id);
//    $("#ipNameArticleTypeDescription").val(data.name);
//    $("#textareaNoteArticleTypeDescription").val(data.description);
//    $("#ipMinimumStockArticleTypeDescription").val(data.minimumStock);
//    var title = "<span class=\"glyphicon glyphicon-info-sign\" style=\"margin: 0px 15px 0px 0px\"></span>Informationen zu Artikelart: <span id=\"spanItemPanelTitle\"  style=\"font-weight: bold; color: blue\">" + data.name + "</span>";
//    $("#panelTitelArticleTypeDescription").html(title);
//}
//
//
///**
// * Returns html table content as <tr> tags 
// * @param {type} data
// * @returns {String}
// */
//function displayArticleTypes(data) {
//    var rows = "";
//    for (var i = 0; i < data.length; i++) {
//        rows += "<tr><td>" + data[i].id + "</td><td>" + data[i].name + "</td><td>" + data[i].description + "</td><td>" + data[i].minimumStock + "</td></tr>";
//    }
//    return rows;
//}
//


//--------------------------------------------------------------------------------------------------


//function getEntry()
//{

//    xmlHttpReq = new XMLHttpRequest();
//    if (xmlHttpReq)
//    {
//        var elem = document.getElementById("SqlQueryText");
//        var url = "http://localhost:8080/StockWebPrj/webresources/generic/entries/" + elem.value;
////        alert(url);
//        xmlHttpReq.open('GET', url, true);
//        xmlHttpReq.onreadystatechange = getEntryResponseHandler;
//        xmlHttpReq.send(null);
////        var label = document.getElementById("infoLabel");
////        label.value = url;
//    }
//    else
//    {
//        alert("Cannot create XMLHttpRequest!!!");
//    }
//}



//function getEntryResponseHandler()
//{
//    var localReadyState = xmlHttpReq.readyState;
//    if (localReadyState === 4)
//    {
//        var elem = document.getElementById("SqlQueryResult");
//        elem.value = xmlHttpReq.responseText;
////        alert("Response Text: " + xmlHttpReq.responseText + "\nelem.innerHTML: " + elem.innerHTML);
//    }
//}



//
//
//
//
////
//$(document).ready(function() {
//
//    $("#SqlQuerySubmit").click(function() {
//        alert("HALLO");
//        var url = "http://localhost:8080/StockWebPrj/webresources/generic/entries/" + $("#SqlQueryText").value;
//        $.ajax({
//            "url": url,
//            "type": "get",
////            "dataType": "json",
//            "success": function(data) {
//                $("#SqlQueryResult").value = data;
//            }
//        });
//    });
//});


/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


//$(function(){
//    $(".clickMe").mouseenter(function(){
//        $(this).children(".frame").slideToggle("slow");
//    });
//});
//
//$(function(){
//    $(".clickMe").mouseleave(function(){
//        $(this).children(".frame").slideToggle("slow");
//    });
//});
//
//function listEntriesAsList()
//{
//    xmlHttpReq = new XMLHttpRequest();
//    if (xmlHttpReq)
//    {
//        var url = "http://localhost:8080/TestGuestbookJaxrs/webresources/testguestbook/entries/";
////        alert(s);
//        xmlHttpReq.open('GET', url, true);
//        xmlHttpReq.setRequestHeader("Accept", "text/html");
//        xmlHttpReq.onreadystatechange = getEntryResponseHandler;
//        xmlHttpReq.send(null);
//        var label = document.getElementById("infoLabel");
//        label.value = url;
//    }
//    else
//    {
//        alert("Cannot create XMLHttpRequest!!!");
//    }
//
//}
//
//
//function createEntry()
//{
//    xmlHttpReq = new XMLHttpRequest();
//    if (xmlHttpReq)
//    {
//        var url = "http://localhost:8080/TestGuestbookJaxrs/webresources/testguestbook/entries/";
//        xmlHttpReq.open('POST', url, true);
//        xmlHttpReq.setRequestHeader("Accept", "text/html");
//        xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//        xmlHttpReq.onreadystatechange = getEntryResponseHandler;
//        var payload = "Name=" + document.getElementById("inNameCreate").value + "&";
//        payload = payload + "Note=" + document.getElementById("txtareaMsgCreate").value;
////        alert(payload);
//        xmlHttpReq.send(payload);
//        var label = document.getElementById("infoLabel");
//        label.value = url;
//    }
//}
//
//function getEntryResponseHandler()
//{
//    var localReadyState = xmlHttpReq.readyState;
//    if (localReadyState === 4)
//    {
//        var elem = document.getElementById("result");
//        elem.value = xmlHttpReq.responseText;
//        //alert("Response Text: " + xmlHttpReq.responseText + "\nelem.innerHTML: " + elem.innerHTML);
//    }
//}