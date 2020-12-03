package com.bzamani.framework.model.security;

import com.bzamani.framework.model.BaseEntity;
import com.bzamani.framework.model.core.organization.Organization;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "CORE_USER", uniqueConstraints = {@UniqueConstraint(name = "unq_username", columnNames = "username")})
@SequenceGenerator(name = "sequence_db", sequenceName = "SEQ_CORE_USER", allocationSize = 1)
@Setter
@Getter
@FilterDefs({@FilterDef(name = "testFilter", parameters = {@ParamDef(name = "userId", type = "long")})})
@Filters({@Filter(name = "testFilter", condition = " id = :userId ")})
public class User extends BaseEntity implements UserDetails {

  /*@Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQG_CORE_USER")
  private Long id;*/

    @NotNull
    @Column(name = "USERNAME", length = 30, nullable = false)
    private String username;

    @NotNull
    @Column(name = "PASSWORD", length = 69, nullable = false)
    @Getter
    @Setter
    private String password;

    @NotNull
    @Column(name = "firstname", length = 30, nullable = false)
    @Getter
    @Setter
    private String firstname;

    @NotNull
    @Column(name = "lastname", length = 30, nullable = false)
    @Getter
    @Setter
    private String lastname;

    @NotNull
    @Column(name = "is_accountNonExpired", nullable = false)
    @Getter
    @Setter
    private boolean accountNonExpired;

    @NotNull
    @Column(name = "is_accountNonLocked", nullable = false)
    @Getter
    @Setter
    private boolean accountNonLocked;

    @NotNull
    @Column(name = "is_credentialsNonExpired", nullable = false)
    @Getter
    @Setter
    private boolean credentialsNonExpired;

    @NotNull
    @Column(name = "IS_ENABLED", nullable = false)
    @Getter
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
    @JoinTable(name = "CORE_USER_ACTION",
            joinColumns =
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns =
            @JoinColumn(name = "ACTION_ID", referencedColumnName = "ID", nullable = false)
    )
    private Set<Action> actions;

    @Transient
    private Set<GrantedAuthority> authorities;

    @Transient
    private String token;

}
