function viewFancyBox(url, width, height, beforeCloseAction, afterCloseFunction) {
    $.fancybox({
        fitToView: false,
        width: width,
        height: height,
        autoSize: false,
        closeClick: false,
        beforeClose: function () {
            if (beforeCloseAction != null && typeof beforeCloseAction == 'function')
                beforeCloseAction();
        },
        afterClose: function () {
            if (afterCloseFunction != null && typeof afterCloseFunction == 'function')
                afterCloseFunction();
        },
        openEffect: 'none',
        closeEffect: 'none',
        type: 'iframe',
        href: url,
        onCancel: function () {
            if (cancleCallback != null && typeof cancleCallback == 'function')
                cancleCallback();
        }
    });
}

function fillComboBaseInfo(headerId, elementId) {
    let contextPath = "/app";
    $.ajax({
        type: "GET",
        url: contextPath + "/api/public/baseinfo/getAllByHeaderId/" + headerId,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (res) {
            $("#" + elementId).append("<option value=-1>...</option>");
            $.each(res, function (index, value) {
                $("#" + elementId).append("<option value=" + value.id + ">" + value.title + "</option>");
            });
        },
        error: function (err) {
        }
    });
}

function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'), sParameterName, i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
}

function initializeDatePicker() {
    $(".mydatepicker").persianDatepicker({
        "inline": false,
        "format": "YYYY/MM/DD",
        "viewMode": "day",
        "initialValue": true,
        //"minDate": 1609085457485,
        //"maxDate": 1610035857493,
        "autoClose": true,
        "position": "auto",
        "onlyTimePicker": false,
        "onlySelectOnDate": true,
        "calendarType": "persian",
        "inputDelay": 800,
        "observer": true,
        "calendar": {
            "persian": {
                "locale": "en",
                "showHint": false,
                "leapYearMode": "algorithmic"
            },
            "gregorian": {
                "locale": "en",
                "showHint": false
            }
        },
        "navigator": {
            "enabled": true,
            "scroll": {
                "enabled": false
            },
            "text": {
                "btnNextText": "<<",
                "btnPrevText": ">>"
            }
        },
        "toolbox": {
            "enabled": true,
            "calendarSwitch": {
                "enabled": false,
                "format": "MMMM"
            },
            "todayButton": {
                "enabled": true,
                "text": {
                    "fa": "امروز",
                    "en": "امروز"
                }
            },
            "submitButton": {
                "enabled": false,
                "text": {
                    "fa": "تایید",
                    "en": "Submit"
                }
            },
            "text": {
                "btnToday": "امروز"
            }
        },
        "timePicker": {
            "enabled": false,
            "step": 1,
            "hour": {
                "enabled": true,
                "step": null
            },
            "minute": {
                "enabled": true,
                "step": null
            },
            "second": {
                "enabled": true,
                "step": null
            },
            "meridian": {
                "enabled": true
            }
        },
        "dayPicker": {
            "enabled": true,
            "titleFormat": "YYYY MMMM"
        },
        "monthPicker": {
            "enabled": true,
            "titleFormat": "YYYY"
        },
        "yearPicker": {
            "enabled": true,
            "titleFormat": "YYYY"
        },
        "responsive": true
    });
}