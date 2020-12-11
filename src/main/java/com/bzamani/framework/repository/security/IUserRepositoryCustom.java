package com.bzamani.framework.repository.security;

import com.bzamani.framework.model.security.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IUserRepositoryCustom {
    List<User> getAllAuthorized();
}
