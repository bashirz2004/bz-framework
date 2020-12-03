package com.bzamani.framework.config.security;

import com.bzamani.framework.controller.BaseController;
import com.bzamani.framework.model.security.User;
import com.bzamani.framework.service.security.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
public class AuthenticationController extends BaseController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private IUserService iUserService;

  @Autowired
  private JwtUtil jwtUtil;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public User createAuthenticationToken(@RequestBody Map<String, String> loginInfo) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.get("username"), loginInfo.get("password")));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }

    UserDetails userdetails = userDetailsService.loadUserByUsername(loginInfo.get("username"));
    User user = iUserService.findUserByUsernameEquals(loginInfo.get("username"));
    user.setToken(jwtUtil.generateToken(userdetails));
    return user;
  }
}
