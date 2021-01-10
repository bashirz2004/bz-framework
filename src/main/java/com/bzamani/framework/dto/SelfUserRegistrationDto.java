package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelfUserRegistrationDto {
    private Long userId;
    private String firstname;
    private String lastname;
    private String username;
    private String oldPassword;
    private String password;
    private String passwordRepeat;
    private Boolean male;
    private String nationalCode;
    private String email;
    private String userExpireDateShamsi;
    private String passwordExpireDateShamsi;

}
