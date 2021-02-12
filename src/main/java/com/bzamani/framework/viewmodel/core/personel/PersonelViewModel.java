package com.bzamani.framework.viewmodel.core.personel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonelViewModel {

    private Long id;

    private String firstname;

    private String lastname;

    private boolean male;

    private String mobile;

    private String address;

    private String telephone;

    private String fatherName;

    private String motherName;

    private String birthCertificateNumber;

    private String nationalCode;

    private String email;

    private String birthPlace;

    private String fileCode;
//---------------------------------------------------

    private Long educationLevelId;
    private String educationLevelTitle;

    private Long militaryServiceStatusId;
    private String militaryServiceStatusTitle;

    private Long organizationId;
    private String organizationTitle;

    private Long stateId;
    private String stateTitle;

    private Long cityId;
    private String cityTitle;

    private Long regionId;
    private String regionTitle;
}
