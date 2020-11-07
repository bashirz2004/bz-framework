package com.bzamani.framework.model.security;

import com.bzamani.framework.model.core.organization.Organization;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Setter
@Getter
public class UserViewModel {
  private Long id;
  private String username;
  private String password;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
  private Set<Organization> organizations;
  private Set<Organization> excludedOrganizations;
  private Set<GrantedAuthority> authorities;
  private String token;

}
