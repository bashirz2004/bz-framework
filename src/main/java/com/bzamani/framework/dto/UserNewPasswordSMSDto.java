package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNewPasswordSMSDto {
    private String originator;
    private String[] recipients;
    private String message;

    public UserNewPasswordSMSDto() {
    }

    public UserNewPasswordSMSDto(String originator, String[] recipients, String message) {
        this.originator = originator;
        this.recipients = recipients;
        this.message = message;
    }

}


