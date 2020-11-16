package com.bzamani.framework.service.security;

import com.bzamani.framework.model.security.User;
import com.bzamani.framework.repository.security.IUserRepository;
import com.bzamani.framework.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends GenericService<User, Long> implements IUserService {

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
  @Transactional
  public User save(User user) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    return iUserRepository.save(user);
  }

  @Override
  public User authenticate(String username, String password) {
    User user = findUserByUsernameEquals(username);
    if (user != null) {
      PasswordEncoder encoder = new BCryptPasswordEncoder();
      if (encoder.matches(password, user.getPassword()))
        return user;
      else return null;
    } else
      return null;
  }

}
