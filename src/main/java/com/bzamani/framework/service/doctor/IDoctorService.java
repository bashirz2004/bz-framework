package com.bzamani.framework.service.doctor;

import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.service.IGenericService;

import java.util.Map;

public interface IDoctorService extends IGenericService<Doctor, Long> {
    Map<String, Object> searchDoctors(String firstname, String lastname, String medicalNationalNumber, Boolean male,
                                      Long stateId, Long cityId, Long regionId,Long specialityId, String address, String telephone, int page, int size, String[] sort);

}
