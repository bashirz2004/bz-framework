package com.bzamani.framework.model.baseinfo;

import com.bzamani.framework.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "core_baseinfo_header", uniqueConstraints = {@UniqueConstraint(name = "unq_bih_title", columnNames = {"title"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_core_baseinfo_header", allocationSize = 1)
@Setter
@Getter
public class BaseInfoHeader extends BaseEntity {
    @NotNull
    @Column(name = "title")
    private String title;
}
