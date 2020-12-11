package com.bzamani.framework.repository.security;

import com.bzamani.framework.config.security.SecurityUtility;
import com.bzamani.framework.model.security.User;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class IUserRepositoryImpl implements IUserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAllAuthorized() {
        Filter filter = (Filter) entityManager.unwrap(Session.class).enableFilter("testFilter");
        filter.setParameter("username", SecurityUtility.getAuthenticatedUser().getUsername());
        List<User> result = (List<User>) entityManager.createQuery(" from " + User.class.getName()).getResultList();
        entityManager.unwrap(Session.class).disableFilter("testFilter");
        return result;
    }
}
