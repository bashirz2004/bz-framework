package com.bzamani.framework.model.core.user;

import javax.persistence.*;

@Entity
@Table(name = "core_user")
@lombok.Setter
@lombok.Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "token")
  private String token;

  @Column(name = "active")
  private boolean active;

  public User() {

  }


}
