package com.bzamani.framework.model.core.action;

import com.bzamani.framework.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

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

    @Column(name = "src", length = 150)
    private String src;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Action parent;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "menu")
    private Boolean menu;

    @Column(name = "icon_class", length = 30)
    private String iconClass;

    @Column(name = "hierarchy_code", updatable = false)
    private String hierarchyCode;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @OrderBy("title")
    private Set<Action> children;

}
