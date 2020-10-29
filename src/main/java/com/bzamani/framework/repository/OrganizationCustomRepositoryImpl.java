package com.bzamani.framework.repository;

import com.bzamani.framework.model.Organization;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class OrganizationCustomRepositoryImpl implements OrganizationCustomRepository {
    //dynamicQuery
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Organization> mySearch(String title, String description, Boolean active) {
        String hql = " select e from " + Organization.class.getName() + " e where 1=1 ";

        if (title != null && title.length() > 0)
            hql += " and e.title like '%" + title + "%'";

        if (description != null && description.length() > 0)
            hql += " and e.description like '%" + description + "%'";

        if (active != null)
            hql += " and e.active = " + active ;

        return (List<Organization>) entityManager.createQuery(hql).getResultList();
    }
}
