function showMessage(title, message, type) {
    swal({
        title: title,
        text: message,
        type: type, //success,error,info,...
        showCloseButton: true,
        showCancelButton: false,
        confirmButtonText: "ok",
        timer: 10000
    }).then(function () {
        //return okCallback();
    }, function () {

    });
}

function showConfirm(title, message, okCallback, cancelCallback) {
    swal({
        title: title,
        text: message,
        type: "question",
        showCloseButton: true,
        showCancelButton: true,
        confirmButtonText: "بله",
        cancelButtonText: "خیر"
    }).then(function () {
        return okCallback();
    }, function () {
        if (cancelCallback)
            return cancelCallback();
        else
            return null;
    });
}
