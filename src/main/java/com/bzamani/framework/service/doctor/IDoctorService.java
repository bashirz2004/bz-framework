package com.bzamani.framework.service.doctor;

import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.service.core.IGenericService;

import java.util.Map;

public interface IDoctorService extends IGenericService<Doctor, Long> {
    Map<String, Object> searchDoctors(String firstname, String lastname, String medicalNationalNumber, Boolean male,
                                      Long stateId, Long cityId, Long regionId,Long specialityId,String specialityTitle,
                                      String address, String telephone,String specialities,String genders, int page, int size, String[] sort);

    Map<String, Object> searchDoctorAuthorize(String firstname, String lastname, int page, int size, String[] sort);

    Doctor getAuthenticatedDoctor();
}
