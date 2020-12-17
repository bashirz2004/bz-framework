package com.bzamani.framework.common.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorMesaage {
    private HttpStatus httpStatus;
    private String code;
    private String message;

    public ErrorMesaage() {

    }

    public ErrorMesaage(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
