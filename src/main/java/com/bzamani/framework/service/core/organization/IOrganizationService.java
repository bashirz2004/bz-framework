package com.bzamani.framework.service.core.organization;

import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;
import java.util.Map;

public interface IOrganizationService extends IGenericService<Organization, Long> {

    Map<String, Object> getAllGridByMyQuery(String title, Boolean active, int page, int size, String[] sort);

    List<Organization> getAllByParentId(Long parentId);

}
