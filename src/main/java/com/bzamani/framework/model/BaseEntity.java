package com.bzamani.framework.model;

import com.bzamani.framework.model.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "creator", "lastUpdater"})
public abstract class BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_db")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  @JsonIgnore
  private User creator;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lastUpdater")
  @JsonIgnore
  private User lastUpdater;

  @Column(name = "create_date")
  private Date createDate = new Date();

  @Column(name = "last_update_date")
  private Date lastUpdateDate = new Date();
}
