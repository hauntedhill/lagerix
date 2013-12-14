
//------------------------------------------------------------------------------
//global settings---------------------------------------------------------------
//------------------------------------------------------------------------------

var urlGlobal = "";
var selectedStorageId = "";
$(document).ready(function() {
    urlGlobal = $(document.forms["formArticleTypeDescription"]).attr("action");
    $("input[name='ipMinimumStock']").attr('disabled', 'disabled');
});

//logout routine
$(document).on("click", "#btnLogOut", function() {
    var urli = "https://" + window.location.host + "/lagerix/services/auth/logout";
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

//global error handler
function errorHandler(jqXHR, textStatus, errorThrown) {
    window.location.replace("http://" + window.location.hostname + ":8080/lagerix");
}


//------------------------------------------------------------------------------
//articletype selecting --------------------------------------------------------
//------------------------------------------------------------------------------

//select article type in advanced search result table
$(document).on("click", ".articleTypeRow", function() {
    simpleSearch("ipIdSimpleSearch=" + $(this).children(".articleTypeId").html());
});

//change cursor on enter row in advanced search result table
$(document).on("mouseenter", ".articleTypeRow", function() {
    $(this).css('cursor', 'pointer');
});

//change cursor on leave row in advanced search result table
$(document).on("mouseleave", ".articleTypeRow", function() {
    $(this).css('cursor', 'auto');
});

$(document).on("click", "#aMinUnderrun", function(){
    refreshMinUnderrun();
});

//advanced search for article types
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
            },
            error: function(jqXHR, textStatus, errorThrown) {
                errorHandler(jqXHR, textStatus, errorThrown);
            }
        });
        return false;
    });
});

//prompt a simple search after ID for article types
$(document).ready(function() {
    $(document.forms['simpleSearchForm']).submit(function() {
        simpleSearch($("#simpleSearchForm").serialize());
        return false;
    });
});

//search for article types with underrun minimum stock
$(document).ready(function() {
    $(document.forms['formRefreshMinUnderrun']).submit(function() {
        refreshMinUnderrun();
        return false;
    });
});

//refresh article type display
$(document).on("click", "#btnRefreshArticleType", function() {
    simpleSearch("ipIdSimpleSearch=" + $("#ipArticleTypeId").val());
    return false;
});

//execute a simple search in an ajax request
function simpleSearch(pData) {
    $.ajax({
        url: urlGlobal + "simplesearch",
        type: "GET",
        data: pData,
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            displayArticleType(data, textStatus, jqXHR);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
}

//Refreshes the display of the article types with underrun minimum stock
function refreshMinUnderrun() {
    $.ajax({
        url: urlGlobal + "underrunminstocks",
        type: "GET",
        data: "",
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            $("#tbodyUnderrunMinStock").html(displayArticleTypes(data));
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
}

//displays the founded article types of an advanced or minimum stock underrun search
function displayArticleTypes(data) {
    var rows = "";
    for (var i = 0; i < data.length; i++) {
        rows += "<tr class=\"articleTypeRow\"><td class=\"articleTypeId\">" + data[i].id + "</td><td>" + data[i].name + "</td><td>" + data[i].description + "</td><td>" + data[i].minimumStock + "</td></tr>";
    }
    return rows;
}

//displays the stock trend of an selected article type in a table
function displayStockTrend(data) {
    var rows = "";
    var bookingDirection = "";
    var stock = 0;
    var stockInsert = "";
    for (var i = 0; i < data.length; i++) {
        if (data[i].bookedIn) {
            bookingDirection = "Einbuchung";
            stock++;
        } else {
            bookingDirection = "Ausbuchung";
            stock--;
        }
        if (stock < parseInt($("#ipMinimumStock").val()))
        {
            stockInsert = "<span style=\"color:#ff0000\">" + stock + "</span>";
        }
        else
        {
            stockInsert = stock;
        }
        rows += "<tr><td>" + formatDate(new Date(data[i].timestamp), '%d.%M.%Y - %H:%m:%s') + "</td><td>" + bookingDirection + "</td><td>" + stockInsert + "</td></tr>";
    }
    return rows;
}

//date format function
function formatDate(date, fmt) {
    function pad(value) {
        return (value.toString().length < 2) ? '0' + value : value;
    }
    return fmt.replace(/%([a-zA-Z])/g, function(_, fmtCode) {
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

//displayes a selected article type
function displayArticleType(data, textStatus, jqXHR) {
    if (data.id !== 0)
    {
        $("#ipArticleTypeId").val(data.id);
        $("#ipName").val(data.name);
        $("#textareaDescription").val(data.description);
        $("#ipMinimumStock").val(data.minimumStock);
        $("#ipStorageId").val(data.storageId);
        $("#ipStorageName").val(data.storageName);
        $("#ipCurrentStock").val(data.currentStock);
        $("#tbodyStockTrend").html(displayStockTrend(data.movements));

        var title = "<span class=\"glyphicon glyphicon-info-sign\" style=\"margin: 0px 15px 0px 0px\"></span>Informationen zu Artikelart: <span id=\"spanItemPanelTitle\"  style=\"font-weight: bold; color: #68838B\">" + data.name + "</span>";
        $("#panelTitelArticleTypeDescription").html(title);

        if (parseInt($("#ipMinimumStock").val()) > parseInt($("#ipCurrentStock").val()))
        {
            $("#ipCurrentStock").css("color", "#ff0000");
        }
        else
        {
            $("#ipCurrentStock").css("color", "#000000");
        }
        getStorage("storageId=" + data.storageId);
    }
    else
    {
        alert("Keine Artikelart zu angegebener ID gefunden!");
    }
}

//------------------------------------------------------------------------------
//storrage display--------------------------------------------------------------
//------------------------------------------------------------------------------

//change cursor on entering a row in storage table
$(document).on("mouseenter", ".storageRow", function() {
    $(this).css('cursor', 'pointer');
});

//change cursor on leaving a row in storage table
$(document).on("mouseleave", ".storageRow", function() {
    $(this).css('cursor', 'auto');
});

//expand and collapse storage view
$(document).on("click", "#aStorages", function() {
    getStorages();
});

//prompt the display of a storage after selecting in storage table
$(document).on("click", ".storageRow", function() {
    getStorage("storageId=" + $(this).children(".storageId").html());
});

//refresh display of storages
$(document).on("click", "#btnRefreshStorages", function() {
    getStorages();
    getStorage("storageId=" + selectedStorageId);
});

//requests a storage using ajax
function getStorage(pData) {
    $.ajax({
        url: urlGlobal + "storage",
        type: "GET",
        data: pData,
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            displayStorage(data, textStatus, jqXHR);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
}

//displays a selected storage
function displayStorage(data, textStatus, jqXHR) {
    var content = "";
    for (var i = 0; i < data.yards.length; i++) {
        var bgColor = data.yards[i].articleTypeId == 0 ? " style=\"background-color: #BCED91\"" : "";
        var articleType = data.yards[i].articleTypeId == 0 ? "" : "<span class=\"lagerix-yard-span\">" + data.yards[i].articleTypeName + "</span><br><span class=\"lagerix-yard-span\">Artikelart-ID: " + data.yards[i].articleTypeId + "</span><br><span class=\"lagerix-yard-span\">Artikel-ID: " + data.yards[i].articleId + "</span>";
        content += "<div class=\"well lagerix-yard\"" + bgColor + "><span style=\"font-size: 14; font-weight: bolder\">" + data.yards[i].id + "</span><br>" + articleType + "</div>";
    }
    var title = "Lagerbelegung " + data.name;
    $("#aStorages").html(title);
    $("#divStorage").html(content);
    selectedStorageId = data.id;
}

//requests all storages using ajax
function getStorages() {
    $.ajax({
        url: urlGlobal + "storages",
        type: "GET",
        data: "",
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            $("#tbodyStorages").html(displayStorages(data));
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
}

//displays all storages
function displayStorages(data) {
    var rows = "";
    for (var i = 0; i < data.length; i++) {
        rows += "<tr class=\"storageRow\"><td class=\"storageId\">" + data[i].id + "</td><td>" + data[i].name + "</td></tr>";
    }
    return rows;
}

//------------------------------------------------------------------------------
//minimum stock functions-------------------------------------------------------
//------------------------------------------------------------------------------

//implementation of user-friendly minimum stock changing in gui
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

//changes minimum stock of an article type using ajax
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
        success: function(data, textStatus, jqXHR) {
            if (data != 0)
            {
                alert("Mindestbestand konnte nicht geändert werden!");
            }
            simpleSearch("ipIdSimpleSearch=" + $("#ipArticleTypeId").val());
            $("#btnChangeMinimumStock").click();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
    return false;
});

//abort minimum stock changing
$(document).on("click", "#btnChangeMinimumStockAbort", function() {
    $("#btnChangeMinimumStock").click();
    simpleSearch("ipIdSimpleSearch=" + $("#ipArticleTypeId").val());
    return false;
});







