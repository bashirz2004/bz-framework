package com.bzamani.framework.model.core.action;

import com.bzamani.framework.model.core.BaseEntity;
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

    @Column(name = "src", length = 150)
    private String src;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Action parent;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "menu")
    private Boolean menu;

    @Column(name = "icon_class", length = 30)
    private String iconClass;

   /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<Action> children;*/

}
