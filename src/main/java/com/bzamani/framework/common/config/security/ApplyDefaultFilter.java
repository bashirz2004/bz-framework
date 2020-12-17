package com.bzamani.framework.common.config.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Aspect
@Component
class ApplyDefaultFilter {

    @AfterReturning(
            pointcut = "bean(entityManagerFactory) && execution(* createEntityManager(..))",
            returning = "retVal")
    public void getSessionAfter(JoinPoint joinPoint, Object retVal) {
        if (retVal != null && EntityManager.class.isInstance(retVal) && SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            Session session = ((EntityManager) retVal).unwrap(Session.class);
            session.enableFilter("organizationAuthorize").setParameter("username", SecurityContextHolder.getContext().getAuthentication() != null ? ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() : "");
        }
    }

}


