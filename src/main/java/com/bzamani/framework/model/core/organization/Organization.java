package com.bzamani.framework.model.core.organization;

import com.bzamani.framework.common.config.mycustomannotation.MyLengthValidator;
import com.bzamani.framework.model.core.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CORE_ORGANIZATION", uniqueConstraints = {@UniqueConstraint(name = "unq_parent_title", columnNames = {"parent_id", "title"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "SEQ_CORE_ORGANIZATION", allocationSize = 1)
@Setter
@Getter
@FilterDefs({@FilterDef(name = "organizationAuthorize", parameters = {@ParamDef(name = "username", type = "string")})})
@Filters({@Filter(name = "organizationAuthorize", condition = " creator = (select a.id from core_user a where a.username = :username )")})
public class Organization extends BaseEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Organization parent;

  @MyLengthValidator(minLenght = 0, maxLength = 50, message = "Error. length of field 'title' is not valid ! ")
  @Column(name = "title")
  private String title;

  @MyLengthValidator(minLenght = 0, maxLength = 50, message = "Error. length of field 'description' is not valid ! ")
  @Column(name = "description")
  private String description;

  @Column(name = "active")
  private boolean active;

  public Organization() {

  }


}
