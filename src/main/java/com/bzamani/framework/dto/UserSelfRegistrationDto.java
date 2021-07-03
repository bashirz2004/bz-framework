package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSelfRegistrationDto {
    private String mobile;
    private String password;
    private String passwordRepeat;

    private String oldPassword;
    private Long userId;
    private String firstname;
    private String lastname;
    private Boolean male;
    private String nationalCode;
    private String email;
    private String userExpireDateShamsi;
    private String passwordExpireDateShamsi;

}
