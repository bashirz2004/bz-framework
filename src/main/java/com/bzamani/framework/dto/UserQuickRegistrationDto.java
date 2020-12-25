package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQuickRegistrationDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String passwordRepeat;
    private Boolean male;
    private String nationalCode;

}
