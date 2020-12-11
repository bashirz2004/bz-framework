package com.bzamani.framework.service.security.user;

import com.bzamani.framework.model.security.User;
import com.bzamani.framework.service.IGenericService;

import java.util.List;

public interface IUserService extends IGenericService<User, Long> {
    User findUserByUsernameEquals(String username);
    User registerUserByHimself(User user);

    List<User> getAllAuthorized();
}
