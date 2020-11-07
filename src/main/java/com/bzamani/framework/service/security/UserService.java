package com.bzamani.framework.service.security;

import com.bzamani.framework.model.security.User;
import com.bzamani.framework.repository.security.IUserRepository;
import com.bzamani.framework.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User,Long> implements IUserService {

  @Autowired
  IUserRepository iUserRepository;

  @Override
  protected JpaRepository<User, Long> getGenericRepo() {
    return iUserRepository;
  }

  @Override
  public User findUserByUsernameEquals(String username) {
    return iUserRepository.findUserByUsernameEquals(username);
  }


  @Override
  public User create(User user) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    return iUserRepository.save(user);
  }

}
