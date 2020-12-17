package com.bzamani.framework.common;

import com.bzamani.framework.common.core.ErrorMesaage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    // Catch file size exceeded exception!
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<ErrorMesaage> handleMultipartException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        System.out.println("Error: " + ex.getMessage());
        ErrorMesaage errorMesaage = new ErrorMesaage(status,"", ex.getMessage());
        return new ResponseEntity<ErrorMesaage>(errorMesaage,null,status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
