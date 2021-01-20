package com.bzamani.framework.service.clinic;

import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface IClinicService extends IGenericService<Clinic, Long> {
    Map<String, Object> searchClinic(String organizationTitle, String organizationAddress, Long stateId, Long cityId, Long regionId, int page, int size, String[] sort);

}
