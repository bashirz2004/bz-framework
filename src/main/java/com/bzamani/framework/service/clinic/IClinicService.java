package com.bzamani.framework.service.clinic;

import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.service.core.IGenericService;

import java.util.Map;

public interface IClinicService extends IGenericService<Clinic, Long> {
    Clinic saveClinic(Clinic clinic);
    Map<String, Object> searchClinic(String organizationTitle, String organizationAddress, Long stateId, Long cityId, Long regionId,
                                     Boolean confirmed, Boolean showInVipList, int page, int size, String[] sort);

}
