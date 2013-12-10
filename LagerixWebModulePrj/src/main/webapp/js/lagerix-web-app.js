//$(document).on("change", "#ipArticleTypeId", function() {
//    alert("Artikel ID hat sich geändert!!!");
//});



//$( "input[type='text']" ).change(function() {
//// Check input( $( this ).val() ) for validity here
//});

//------------------------------------------------------------------------------
//global settings---------------------------------------------------------------
//------------------------------------------------------------------------------

var urlGlobal = "";

$(document).ready(function() {
    urlGlobal = $(document.forms["formArticleTypeDescription"]).attr("action");
    $("input[name='ipMinimumStock']").attr('disabled', 'disabled');
});

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

function errorHandler(jqXHR, textStatus, errorThrown) {
    window.location.replace("http://" + window.location.hostname + ":8080/lagerix");
}


//------------------------------------------------------------------------------
//articletype selecting --------------------------------------------------------
//------------------------------------------------------------------------------

$(document).on("click", ".articleTypeRow", function() {
    simpleSearch("ipIdSimpleSearch=" + $(this).children(".articleTypeId").html());
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
            },
            error: function(jqXHR, textStatus, errorThrown) {
                errorHandler(jqXHR, textStatus, errorThrown);
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
            },
            error: function(jqXHR, textStatus, errorThrown) {
                errorHandler(jqXHR, textStatus, errorThrown);
            }
        });
        return false;
    });
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
}

function displayArticleTypes(data) {
    var rows = "";
    for (var i = 0; i < data.length; i++) {
        rows += "<tr class=\"articleTypeRow\"><td class=\"articleTypeId\">" + data[i].id + "</td><td>" + data[i].name + "</td><td>" + data[i].description + "</td><td>" + data[i].minimumStock + "</td></tr>";
    }
    return rows;
}

function displayStockTrend(data) {
    var rows = "";
    var bookingDirection = "";
    for (var i = 0; i < data.length; i++) {
        if (data[i].bookedIn) {
            bookingDirection = "Einbuchung";
        } else {
            bookingDirection = "Ausbuchung";
        }
        rows += "<tr><td>" + formatDate(new Date(data[i].timestamp), '%d.%M.%Y - %H:%m:%s') + "</td><td>" + bookingDirection + "</td></tr>";
    }
    return rows;
}

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
            error: function(jqXHR, textStatus, errorThrown) {
                errorHandler(jqXHR, textStatus, errorThrown);
            }
        });
        getStorage("storageId=" + data.storageId);
    }
    else
    {
        alert("Kein Artikel zu angegebener ID gefunden!");
    }
}

//------------------------------------------------------------------------------
//storrage display--------------------------------------------------------------
//------------------------------------------------------------------------------

$(document).on("mouseenter", ".storageRow", function() {
    $(this).css('cursor', 'pointer');
});

$(document).on("mouseleave", ".storageRow", function() {
    $(this).css('cursor', 'auto');
});

$(document).on("click", "#aStorages", function() {
    getStorages();
});

$(document).on("click", ".storageRow", function() {
    getStorage("storageId=" + $(this).children(".storageId").html());
});

function getStorage(pData) {
    $.ajax({
        url: urlGlobal + "storage",
        type: "GET",
        data: pData,
        cache: false,
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
            $("#divStorage").html(displayStorage(data, textStatus, jqXHR));
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
}

function displayStorage(data, textStatus, jqXHR) {
    var content = "";
    for (var i = 0; i < data.yards.length; i++) {
        var bgColor = data.yards[i].articleTypeId == 0 ? " style=\"background-color: #BCED91\"" : "";
        var articleType = data.yards[i].articleTypeId == 0 ? "" : "Artikelart: "+ data.yards[i].articleTypeName+"<br>Artikelart-ID: "+data.yards[i].articleTypeId ;
        content += "<div class=\"well lagerix-yard\"" + bgColor + "><span style=\"font-size: 14; font-weight: bolder\">" + data.yards[i].id + "</span><br>" + articleType + "</div>";
    }
    var title = "Lagerbelegung " + data.name;
    $("#aStorages").html(title);
    return content;
}

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            errorHandler(jqXHR, textStatus, errorThrown);
        }
    });
    return false;
});

$(document).on("click", "#btnChangeMinimumStockAbort", function() {
    $("#btnChangeMinimumStock").click();
    simpleSearch("ipIdSimpleSearch=" + $("#ipArticleTypeId").val());
    return false;
});







