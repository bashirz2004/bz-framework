package com.bzamani.framework.model.portal;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.personel.Personel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "por_like", uniqueConstraints = {@UniqueConstraint(name = "unq_liker_post", columnNames = {"liker_id", "post_id"})})
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_por_like", allocationSize = 1)
@Setter
@Getter
public class Like extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liker_id")
    private Personel liker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
