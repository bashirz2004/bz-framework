function tmplRowIndex(tmplId, pageNumber, pageSize) {
    i = 0;
    $('#' + tmplId).find('.tmplRowIndex').each(function () {
        i++;
        var rIndex;
        if (pageNumber != null && pageSize != null)
            rIndex = (pageNumber * pageSize) + i;
        else
            rIndex = i;
        $(this).append(rIndex);
    });
}

function fillGrid(templateId, destinationId, url, jsonData, callBackFunction) {
    if (jsonData.size < 5) {
        jsonData.size = 5;
        $('#cmbPageSize').val(5);
    }
    if (jsonData.size > 50) {
        jsonData.size = 50;
        $('#cmbPageSize').val(50);
    }
    $.getJSON(url, jsonData, function (entities) {
        $('#' + destinationId + ' :not(script)').remove();
        if (entities.entityList) {
            $('#' + templateId).tmpl(entities.entityList).prependTo('#' + destinationId);
            tmplRowIndex(destinationId, entities.pageNumber, jsonData.size);
        } else {
            $('#' + templateId).tmpl(entities).prependTo('#' + destinationId);
            tmplRowIndex(destinationId, 0, jsonData.size);
        }
        if (entities.totalRecords == 0) {
            showMessage("موردی یافت نشد.", "", "info");
        }
        $('table.table tbody tr:not([th]):odd').css('backgroundColor', '#c4f0f5');
        $('#totalRecords').html(entities.totalRecords);
        $('#pagingList > li').remove();

        if (entities.currentPage == 0)
            $('#pagingList').append('<li class="page-item"><a class="page-link disabled" > قبلی </a></li>');
        else
            $('#pagingList').append('<li class="page-item"><a class="page-link" onclick="previous()"> قبلی </a></li>');

        for (let i = 0; i < entities.totalPages; i++) {
            if (entities.currentPage == i)
                $('#pagingList').append('<li class="page-item active"><a class="page-link" href="#">' + ++i + '<span class="sr-only">(current)</span></a></li>');
            else
                $('#pagingList').append('<li class="page-item"><a class="page-link" onclick="goTo(' + i + ')">' + ++i + '</a></li>');
            --i;
        }

        if (entities.currentPage == entities.totalPages - 1)
            $('#pagingList').append('<li class="page-item"><a class="page-link disabled" > بعدی </a></li>');
        else
            $('#pagingList').append('<li class="page-item"><a class="page-link" onclick="next()"> بعدی </a></li>');


        if (callBackFunction != null && typeof callBackFunction == 'function')
            callBackFunction();
    });
}

function clearInputs() {
    $('input:hidden').val('');
    $('input:text').val('');
    $('input:file').val('');
    $('select').val('');
    $('textarea').val('');
    $('#uploadedImage').prop("src", "");
}


