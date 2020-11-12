package com.bzamani.framework.config.security;

import com.bzamani.framework.model.security.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtility {
  public static User getAuthenticatedUser() {
    User user = new User();
    user = (User) SecurityContextHolder.getContext().getAuthentication(); //eeror in cast darad
    return user;
  }
}
