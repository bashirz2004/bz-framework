function viewFancyBox(url, width, height, beforeCloseAction,afterCloseFunction) {
    $.fancybox({
        fitToView:	false,
        width:		width,
        height:		height,
        autoSize:	false,
        closeClick:	false,
        beforeClose:function() {
            if(beforeCloseAction !=null && typeof beforeCloseAction=='function')
                beforeCloseAction();
        },
        afterClose : function() {
            if(afterCloseFunction !=null && typeof afterCloseFunction=='function')
                afterCloseFunction();
        },
        openEffect: 'none',
        closeEffect:'none',
        type: 		'iframe',
        href:		url,
        onCancel:	function() {
            if(cancleCallback !=null && typeof cancleCallback=='function')
                cancleCallback();
        }
    });
}