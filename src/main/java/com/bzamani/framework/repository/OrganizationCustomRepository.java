package com.bzamani.framework.repository;

import com.bzamani.framework.model.Organization;

import java.util.List;

public interface OrganizationCustomRepository {
    List<Organization> mySearch(String title, String description, Boolean active);
}
