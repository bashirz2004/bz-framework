package com.bzamani.framework.repository.core.user;

import com.bzamani.framework.model.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByUsernameEquals(String username);
}
