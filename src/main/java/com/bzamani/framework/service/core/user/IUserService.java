package com.bzamani.framework.service.core.user;

import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.IGenericService;

public interface IUserService extends IGenericService<User, Long> {
    User findUserByUsernameEquals(String username);

    User registerUserByHimself(User user);

}
