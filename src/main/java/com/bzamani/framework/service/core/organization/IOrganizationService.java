package com.bzamani.framework.service.core.organization;

import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.service.IGenericService;

import java.util.Map;

public interface IOrganizationService extends IGenericService<Organization, Long> {

  public Map<String, Object> getAllGridByMyQuery(String title, String description, Boolean active, int page, int size, String[] sort);
}
