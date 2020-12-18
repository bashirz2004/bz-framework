package com.bzamani.framework.common;

import com.bzamani.framework.common.core.ErrorMesaage;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    // Catch file size exceeded exception!
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ResponseEntity<ErrorMesaage> handleMultipartException(Throwable ex, WebRequest request) {
        System.out.println("Error: " + ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=utf-8");
        ErrorMesaage errorMesaage = new ErrorMesaage(status, "3333333", ex.getMessage());
        return new ResponseEntity<ErrorMesaage>(errorMesaage, responseHeaders, status);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>("Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ResponseEntity<ErrorMesaage> handleConstraintViolationException(Throwable ex, WebRequest request) {
        System.out.println("Error: " + ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=utf-8");
        ErrorMesaage errorMesaage = new ErrorMesaage(status, "444444", ex.getMessage());
        return new ResponseEntity<ErrorMesaage>(errorMesaage, responseHeaders, status);
    }


}
