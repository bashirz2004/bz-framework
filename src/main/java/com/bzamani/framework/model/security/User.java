package com.bzamani.framework.model.security;

import com.bzamani.framework.model.core.organization.Organization;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "CORE_USER")
@SequenceGenerator(name = "sequence_db", sequenceName = "SEQ_CORE_USER", allocationSize = 1)
@Setter
@Getter
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_db")
  private Long id;

  @NotNull
  @Column(name = "USERNAME", length = 30, nullable = false)
  private String username;

  @NotNull
  @Column(name = "PASSWORD", length = 69, nullable = false)
  @Getter(onMethod = @__(@Override))
  @Setter
  private String password;

  @NotNull
  @Column(name = "is_accountNonExpired", nullable = false)
  @Getter(onMethod = @__(@Override))
  @Setter
  private boolean accountNonExpired;

  @NotNull
  @Column(name = "is_accountNonLocked", nullable = false)
  @Getter(onMethod = @__(@Override))
  @Setter
  private boolean accountNonLocked;

  @NotNull
  @Column(name = "is_credentialsNonExpired", nullable = false)
  @Getter(onMethod = @__(@Override))
  @Setter
  private boolean credentialsNonExpired;

  @NotNull
  @Column(name = "IS_ENABLED", nullable = false)
  @Getter(onMethod = @__(@Override))
  @Setter
  private boolean enabled;


  /*@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PERSONELID", updatable = false, nullable = false)
  private BasePersonel personel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CLASSIFICATION_ID", nullable = false)
  private BaseInformation classification;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users"
    , cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  private Set<Group> groups;*/

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "CORE_USER_ORGANIZATION",
    joinColumns =
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false),
    inverseJoinColumns =
    @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID", nullable = false)
  )
  private Set<Organization> organizations;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "CORE_USER_EXCLUDED_ORGANIZATION",
    joinColumns =
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false),
    inverseJoinColumns =
    @JoinColumn(name = "ORGANIZATION_ID", referencedColumnName = "ID", nullable = false)
  )
  private Set<Organization> excludedOrganizations;

  @Transient
  private Set<GrantedAuthority> authorities;

  @Transient
  private String token;



}
