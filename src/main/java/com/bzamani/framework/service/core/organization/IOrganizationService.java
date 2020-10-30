package com.bzamani.framework.service.core.organization;

import com.bzamani.framework.model.core.organization.Organization;

import java.util.List;
import java.util.Map;

public interface IOrganizationService {
    Organization create(Organization organization);

    Organization load(long id);

    Organization update(long id, Organization organization);

    void delete(long id);

    List<Organization> getAll(String[] sort);

    Map<String, Object> getAllGrid(int page,
                                   int size,
                                   String[] sort);

 /*   List<Organization> getAllByMyQuery(
            String title,
            String description,
            Boolean active);*/

    Map<String, Object> getAllGridByMyQuery(
            String title,
            String description,
            Boolean active,
            int page,
            int size,
            String[] sort);
}
