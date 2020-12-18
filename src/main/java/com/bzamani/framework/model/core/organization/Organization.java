package com.bzamani.framework.model.core.organization;

import com.bzamani.framework.common.config.mycustomannotation.MyLengthValidator;
import com.bzamani.framework.model.core.BaseEntity;
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<Organization> children;

    public Organization() {

    }


}
