package com.bzamani.framework.repository.security;

import com.bzamani.framework.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface IUserRepository extends JpaRepository<User, Long> {

  User findUserByUsernameEquals(String username);


}
