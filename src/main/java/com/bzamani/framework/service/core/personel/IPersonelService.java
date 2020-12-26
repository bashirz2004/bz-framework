package com.bzamani.framework.service.core.personel;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.service.core.IGenericService;

import java.util.Map;

public interface IPersonelService extends IGenericService<Personel, Long> {
    Map<String, Object> searchPersonel(String firstname,
                                       String lastname,
                                       String birthCertificateNumber,
                                       String nationalCode,
                                       Boolean male,
                                       String fatherName,
                                       String motherName,
                                       String birthPlace,
                                       Long educationLevelId,
                                       Long militaryServiceStatusId,
                                       String address,
                                       String telephone,
                                       String mobile,
                                       Long organizationId, int page, int size, String[] sort);
    Personel findByEmailEquals(String email);

}
