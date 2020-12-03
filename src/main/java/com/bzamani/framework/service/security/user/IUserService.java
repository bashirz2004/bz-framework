package com.bzamani.framework.service.security.user;

import com.bzamani.framework.model.security.User;
import com.bzamani.framework.service.IGenericService;

public interface IUserService extends IGenericService<User, Long> {
    User findUserByUsernameEquals(String username);

}
