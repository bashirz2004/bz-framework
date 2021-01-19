package com.bzamani.framework.model.core.organization;

import com.bzamani.framework.common.config.mycustomannotation.MyLengthValidator;
import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CORE_ORGANIZATION", uniqueConstraints = {@UniqueConstraint(name = "unq_parent_title", columnNames = {"parent_id", "title"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "SEQ_CORE_ORGANIZATION", allocationSize = 1)
@Setter
@Getter
public class Organization extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Organization parent;

    @MyLengthValidator(minLenght = 0, maxLength = 50, message = "Error. length of field 'title' is not valid ! ")
    @Column(name = "title")
    private String title;

    @Column(name = "active")
    private boolean active;

    @Column(name = "hierarchy_code",updatable = false)
    private String hierarchyCode;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @OrderBy("title")
    private Set<Organization> children;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "fileCode")
    private String fileCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private BaseInfo state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private BaseInfo city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private BaseInfo region;


}
