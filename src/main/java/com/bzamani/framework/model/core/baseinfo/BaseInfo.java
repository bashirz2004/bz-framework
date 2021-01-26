package com.bzamani.framework.model.core.baseinfo;

import com.bzamani.framework.model.core.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "core_baseinfo", uniqueConstraints = {@UniqueConstraint(name = "unq_baseinfo", columnNames = {"header_id","parent_id", "title"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_core_baseinfo", allocationSize = 1)
@Setter
@Getter
public class BaseInfo extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id",nullable = false)
    private BaseInfoHeader header;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private BaseInfo parent;

    @NotNull
    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "code")
    private String code;

    @Formula(" (select count(*) from core_baseinfo b where b.parent_id = id) ")
    private Long childCount;


}
