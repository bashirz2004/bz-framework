package com.bzamani.framework.model.baseinfo;

import com.bzamani.framework.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "core_baseinfo", uniqueConstraints = {@UniqueConstraint(name = "unq_bi_title", columnNames = {"header_id", "title"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_core_baseinfo_header", allocationSize = 1)
@Setter
@Getter
public class BaseInfo extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "header_id",nullable = false)
    private BaseInfoHeader header;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private BaseInfo parent;

    @NotNull
    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "code")
    private String code;
}
