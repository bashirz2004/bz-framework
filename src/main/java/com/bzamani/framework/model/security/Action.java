package com.bzamani.framework.model.security;

import com.bzamani.framework.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CORE_ACTION", uniqueConstraints = {@UniqueConstraint(name = "unq_action_code", columnNames = {"code"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_core_action", allocationSize = 1)
@Setter
@Getter
public class Action extends BaseEntity {

    @NotNull
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @NotNull
    @Column(name = "code", length = 30, nullable = false)
    private String code;
}
