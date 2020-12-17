package com.bzamani.framework.model.core.user;

import com.bzamani.framework.common.config.mycustomannotation.MyLengthValidator;
import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.model.core.action.Action;
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
@FilterDefs({@FilterDef(name = "organizationAuthorize", parameters = {@ParamDef(name = "username", type = "string")})})
@Filters({@Filter(name = "organizationAuthorize", condition = " username = :username ")})
public class User extends BaseEntity implements UserDetails {

  /*@Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQG_CORE_USER")
  private Long id;*/

    @NotNull
    @MyLengthValidator(minLenght = 1, maxLength = 30, message = "Error. length of field USERNAME is not valid ! ")
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotNull
    @MyLengthValidator(minLenght = 1, maxLength = 69, message = "Error. length of field PASSWORD is not valid ! ")
    @Column(name = "PASSWORD", nullable = false)
    @Getter
    @Setter
    private String password;

    @NotNull
    @MyLengthValidator(minLenght = 1, maxLength = 30, message = "Error. length of field firstname is not valid ! ")
    @Column(name = "firstname", nullable = false)
    @Getter
    @Setter
    private String firstname;

    @NotNull
    @MyLengthValidator(minLenght = 1, maxLength = 30, message = "Error. length of field lastname is not valid ! ")
    @Column(name = "lastname", nullable = false)
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
