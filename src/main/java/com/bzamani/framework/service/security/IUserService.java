package com.bzamani.framework.service.security;

import com.bzamani.framework.model.security.User;
import com.bzamani.framework.service.IGenericService;

public interface IUserService extends IGenericService<User, Long> {
  User findUserByUsernameEquals(String username);

 // User authenticate(String username, String password);
}
