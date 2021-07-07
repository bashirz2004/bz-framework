package com.bzamani.framework.service.homevisit;

import com.bzamani.framework.model.homevisit.HomeVisitRequest;
import com.bzamani.framework.service.core.IGenericService;

import java.util.Map;

public interface IHomeVisitService extends IGenericService<HomeVisitRequest, Long> {
    Map<String, Object> search(String firstname, String lastname, String mobile,Integer requestType, Boolean done, int page, int size, String[] sort);

}
