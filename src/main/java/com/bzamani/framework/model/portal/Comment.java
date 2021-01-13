package com.bzamani.framework.model.portal;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.personel.Personel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "por_comment")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_por_comment", allocationSize = 1)
@Setter
@Getter
public class Comment extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    private Personel commenter;

    @NotNull
    @Column(name = "create_date_shamsi")
    private String create_date_shamsi;

    @NotNull
    @Column(name = "description", length = 255)
    private String description;

    @NotNull
    @Column(name = "confirmed")
    private boolean confirmed;

    @Formula(" (select count(*) from por_like b where b.comment_id = id) ")
    private Long likesCount;

}
