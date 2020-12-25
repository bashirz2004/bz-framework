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