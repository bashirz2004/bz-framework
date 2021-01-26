package com.bzamani.framework.model.core.group;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.action.Action;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "CORE_GROUP")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_core_group", allocationSize = 1)
@Setter
@Getter
public class Group extends BaseEntity {

    @NotNull
    @Column(name = "title", length = 60, nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CORE_GROUP_ACTION",
            joinColumns =
            @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns =
            @JoinColumn(name = "ACTION_ID", referencedColumnName = "ID", nullable = false)
    )
    private Set<Action> actions;


}
