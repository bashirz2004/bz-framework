package com.bzamani.framework.repository.core.user;

import com.bzamani.framework.model.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Long>{


}
