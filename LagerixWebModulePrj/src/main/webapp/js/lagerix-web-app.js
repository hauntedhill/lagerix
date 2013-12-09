//$(document).on("change", "#ipArticleTypeId", function() {
//    alert("Artikel ID hat sich geändert!!!");
//});



//$( "input[type='text']" ).change(function() {
//// Check input( $( this ).val() ) for validity here
//});

var urlGlobal = "";

$(document).ready(function() {
    urlGlobal = $(document.forms["formArticleTypeDescription"]).attr("action");
    $("input[name='ipMinimumStock']").attr('disabled', 'disabled');
});



$(document).on("click", ".articleTypeRow",function(){
    simpleSearch("ipIdSimpleSearch=" + $(this).children(".articleTypeId").html());
});

function simpleSearch(pData) {
    $.ajax({
        url: urlGlobal + "simplesearch",
        type: "GET",
        data: pData,
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            displayArticleType(data, textStatus, jqXHR);
        }
    });
}

$(document).on("change","#ipStorageId2", function(){
    getStorage("storageId="+$("#ipStorageId2").val());
});

function getStorage(pData){
     $.ajax({
        url: urlGlobal + "storage",
        type: "GET",
        data: pData,
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            displayStorage(data, textStatus, jqXHR);
        }
    });
}

function displayStorage(data, textStatus, jqXHR){
    $("#pbStorageOccupancy").html(data);
}

$(document).on("click","#aStorages",function(){
    getStorages();
});

function getStorages(){
    $.ajax({
        url: urlGlobal + "storages",
        type: "GET",
        data: "",
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
         $("#tbodyStorages").html(displayStorages(data));
        }
    });
}

function displayStorages(data){
        var rows = "";
    for (var i = 0; i < data.length; i++) {
        rows += "<tr class=\"storageRow\"><td class=\"storageId\">" + data[i].id + "</td><td>" + data[i].name + "</td></tr>";
    }
    return rows;
}

$(document).on("click", "#btnChangeMinimumStock", function() {
    if ($("#ipArticleTypeId").val() > 0)
    {
        var disabled = ($('input[name=ipMinimumStock]').is(':disabled'));

        if (disabled) {
            $("input[name='ipMinimumStock']").removeAttr('disabled');
            $("button[name='btnChangeMinimumStockAbort']").css('visibility', 'visible');
            $("button[name='btnChangeMinimumStockConfirm']").css('visibility', 'visible');
            $("button[name='btnChangeMinimumStock']").css('visibility', 'hidden');
        }
        else {
            $("input[name='ipMinimumStock']").attr('disabled', 'disabled');
            $("button[name='btnChangeMinimumStockAbort']").css('visibility', 'hidden');
            $("button[name='btnChangeMinimumStockConfirm']").css('visibility', 'hidden');
            $("button[name='btnChangeMinimumStock']").css('visibility', 'visible');
        }
    }
    return false;
});


$(document).on("click", "#btnChangeMinimumStockConfirm", function() {
    var articleTypeId = $("#ipArticleTypeId").val();
    var newMinStock = $("#ipMinimumStock").val();
    $.ajax({
        url: urlGlobal + "minimumstock",
        type: "POST",
        data: "id=" + articleTypeId + "&ipMinimumStock=" + newMinStock,
        cache: false,
        dataType: "text",
        contentType: "application/x-www-form-urlencoded",
        success: function() {
            $("#btnChangeMinimumStock").click();
        }
    });
    return false;
});


$(document).on("click", "#btnChangeMinimumStockAbort", function() {
    $("#btnChangeMinimumStock").click();
    simpleSearch("ipIdSimpleSearch=" + $("#ipArticleTypeId").val());
    return false;
});

$(document).on("click", "#btnLogOut", function() {
    var urli ="https://" + window.location.host + "/lagerix/services/auth/logout";
    $.ajax({
        url: urli,
        type: "GET",
        cache: false,
        dataType: "text",
        success: function(data, textStatus, jqXHR) {
            if (data == "SUCCESS") {
                //redirect to welcome page
                window.location.replace("http://" + window.location.hostname + ":8080/lagerix");
            } else {
                alert("logout failed");
            }
        }
    });
    return false;
});

$(document).on("mouseenter", ".articleTypeRow", function() {
    $(this).css('cursor', 'pointer');
});

$(document).on("mouseleave", ".articleTypeRow", function() {
    $(this).css('cursor', 'auto');
});

$(document).ready(function() {
    $(document.forms['advancedSearchForm']).submit(function() {
        $.ajax({
            url: urlGlobal + "advancedsearch",
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
    $(document.forms['simpleSearchForm']).submit(function() {
        simpleSearch($("#simpleSearchForm").serialize()); 
        return false;
    });
});

$(document).ready(function() {
    $(document.forms['formRefreshMinUnderrun']).submit(function() {
        $.ajax({
            url: urlGlobal + "underrunminstocks",
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
        rows += "<tr class=\"articleTypeRow\"><td class=\"articleTypeId\">" + data[i].id + "</td><td>" + data[i].name + "</td><td>" + data[i].description + "</td><td>" + data[i].minimumStock + "</td></tr>";
    }
    return rows;
}

function displayStockTrend(data) {
    var rows = "";
    var bookingDirection="";
    for (var i = 0; i < data.length; i++) {
        if(data[i].bookedIn){bookingDirection = "Einbuchung"; }else{bookingDirection = "Ausbuchung";}
        rows += "<tr><td>" + formatDate(new Date(data[i].timestamp), '%d.%M.%Y - %H:%m:%s') + "</td><td>"  + bookingDirection + "</td></tr>";
    }
    return rows;
}

function formatDate(date, fmt) {
    function pad(value) {
        return (value.toString().length < 2) ? '0' + value : value;
    }
    return fmt.replace(/%([a-zA-Z])/g, function (_, fmtCode) {
        switch (fmtCode) {
        case 'Y':
            return date.getUTCFullYear();
        case 'M':
            return pad(date.getUTCMonth() + 1);
        case 'd':
            return pad(date.getUTCDate());
        case 'H':
            return pad(date.getUTCHours());
        case 'm':
            return pad(date.getUTCMinutes());
        case 's':
            return pad(date.getUTCSeconds());
        default:
            throw new Error('Unsupported format code: ' + fmtCode);
        }
    });
}





function displayArticleType(data, textStatus, jqXHR) {
    $("#ipArticleTypeId").val(data.id);
    $("#ipName").val(data.name);
    $("#textareaDescription").val(data.description);
    $("#ipMinimumStock").val(data.minimumStock);
    $("#ipStorageId").val(data.storageId);
    $("#ipStorageName").val(data.storageName);
    $("#ipCurrentStock").val(data.currentStock);
    $("#tbodyStockTrend").html(displayStockTrend(data.movements));
    
    
    var title = "<span class=\"glyphicon glyphicon-info-sign\" style=\"margin: 0px 15px 0px 0px\"></span>Informationen zu Artikelart: <span id=\"spanItemPanelTitle\"  style=\"font-weight: bold; color: blue\">" + data.name + "</span>";
    $("#panelTitelArticleTypeDescription").html(title);

    /*get current stock*/
    var url = urlGlobal + "currentstock";
    $.ajax({
        url: url,
        type: "GET",
        data: "id=" + data.id,
        cache: false,
        dataType: "text",
        success: function(data2, textStatus2, jqXHR2) {
            $("#ipCurrentStock").val(data2);
        },
        error: function(jqXHR2, textStatus2, errorThrown2) {
            alert("ERROR!\ntextStatus: " + textStatus2 + "\nerrorThrown: " + errorThrown2 + "\njqXHR.responseText: " + jqXHR2.responseText);
        }
    });

}

