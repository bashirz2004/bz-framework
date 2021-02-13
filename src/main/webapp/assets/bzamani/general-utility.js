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
    $.ajax({
        type: "GET",
        url: "/api/public/baseinfo/getAllByHeaderId/" + headerId,
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
    $(".myDatepicker").persianDatepicker({
        "inline": false,
        "format": "YYYY/MM/DD",
        "viewMode": "day",
        "initialValue": false,
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

function initializeThousandSeparatorOnKeyup() {
    $('.money').simpleMoneyFormat();
}

function toMoneyFormat(inputString) {
    if (inputString == null || inputString.toString().trim().length == 0)
        return;
    var result = '';
    var valueArray = inputString.toString().split('');
    var resultArray = [];
    var counter = 0;
    var temp = '';
    for (var i = valueArray.length - 1; i >= 0; i--) {
        temp += valueArray[i];
        counter++
        if (counter == 3) {
            resultArray.push(temp);
            counter = 0;
            temp = '';
        }
    }
    ;
    if (counter > 0) {
        resultArray.push(temp);
    }
    for (var i = resultArray.length - 1; i >= 0; i--) {
        var resTemp = resultArray[i].split('');
        for (var j = resTemp.length - 1; j >= 0; j--) {
            result += resTemp[j];
        }
        ;
        if (i > 0) {
            result += ','
        }
    }
    return result;
}

function removeMoneyFormat(inputString) {
    if (inputString == null)
        return;
    else
        return inputString.toString().replaceAll(',', '')
}

