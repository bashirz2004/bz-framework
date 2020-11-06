package com.bzamani.framework.config.security;

import com.bzamani.framework.repository.security.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  IUserRepository iUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    com.bzamani.framework.model.security.User user = iUserRepository.findUserByUsernameEquals(username);
    if (user == null)
      throw new UsernameNotFoundException("User not found with the name " + username);

    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + username.toUpperCase()));
    user.setAuthorities(authorities);
    System.out.println("++++++++++++++++++++"+authorities.toArray()[0]);
    return new User(username, user.getPassword(), authorities);
  }

}
