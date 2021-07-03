package com.bzamani.framework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorSelfRegistrationDto {
    private String mobile;
    private String firstname;
    private String lastname;

    private long specialityId;
    private String phone;
    private String address;
    private boolean male;

    private Long stateId;
    private Long cityId;
    private Long regionId;
    private String fileCode;
    private String medicalNationalNumber;
}
