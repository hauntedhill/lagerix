
$(document).ready(function() {
    "use strict";

    $(document.forms['simpleSearchForm']).submit(function(event) {
        var destinationUrl = this.action;

        $.ajax({
            url: destinationUrl,
            type: "GET",
            data: $("#simpleSearchForm").serialize(),
            cache: false,
            dataType: "json",
            success: function(data, textStatus, jqXHR) {
                displayArticleType(data, textStatus, jqXHR);
            }
        });
        //event.preventDefault();
        return false;
    });
});

$(document).ready(function() {
    "use strict";
    $(document.forms['advancedSearchForm']).submit(function(event) {

        var destinationUrl = this.action;

        $.ajax({
            url: destinationUrl,
            type: "GET",
            data: $("#advancedSearchForm").serialize(),
            cache: false,
            dataType: "json",
            success: function(data, textStatus, jqXHR) {
                displayArticleTypes(data, textStatus, jqXHR);
            }
        });
        //event.preventDefault();
        return false;
    });

});

function displayArticleType(data, textStatus, jqXHR) {
    var obj = jQuery.parseJSON(jqXHR.responseText);
//                alert(obj.itemId + "\n" + obj.name + "\n" + obj.note + "\n" + obj.minimumHolding);
    $("#ipArticleTypeIdArticleTypeDescription").val(obj.itemId);
    $("#ipNameArticleTypeDescription").val(obj.name);
    $("#textareaNoteArticleTypeDescription").val(obj.note);
    $("#ipMinimumStockArticleTypeDescription").val(obj.minimumHolding);
    var title = "Informationen zu Artikelart: <span id=\"spanItemPanelTitle\"  style=\"font-weight: bold; color: blue\">" + obj.name + "</span>";
    $("#panelTitelArticleTypeDescription").html(title);
}

function displayArticleTypes(data, textStatus, jqXHR) {
    var arr = jQuery.parseJSON(jqXHR.responseText);
    var options = "";
    for (var i = 0; i < arr.length; i++) {
        options += "<option>" + arr[i].itemId + " | " + arr[i].name + "</option>";
    }
    $("#selectSearchResults").html(options);
////                alert(obj.itemId + "\n" + obj.name + "\n" + obj.note + "\n" + obj.minimumHolding);
//    $("#ipItemIdItemDescription").val(obj.itemId);
//    $("#ipNameItemDescription").val(obj.name);
//    $("#textareaNoteItemDescription").val(obj.note);
//    $("#ipMinimumStockItemDescription").val(obj.minimumHolding);
//    var title = "Informationen zu Artikelart: <span id=\"spanItemPanelTitle\"  style=\"font-weight: bold; color: blue\">" + obj.name + "</span>";
//    $("#panelTitelItemDescription").html(title);
    $("#stockTrend").val(jqXHR.responseText);
}






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