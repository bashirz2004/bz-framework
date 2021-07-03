package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicSelfRegistrationDto {
    private String title;
    private String address;
    private String phone;
    private Long stateId;
    private Long cityId;
    private Long regionId;
    private String fileCode;
}

