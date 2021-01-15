package com.bzamani.framework.model.portal;

import com.bzamani.framework.model.core.BaseEntity;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.personel.Personel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "por_post")
@SequenceGenerator(name = "sequence_db", sequenceName = "seq_por_post", allocationSize = 1)
@Setter
@Getter
public class Post extends BaseEntity {

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private BaseInfo category;

    @NotNull
    @Column(name = "body",length = 4000)
    private String body;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Personel author;

    @NotNull
    @Column(name = "create_date_shamsi")
    private String createDateShamsi;

    @Column(name = "tags")
    private String tags;

    @Column(name = "file_code")
    private String fileCode;

    @NotNull
    @Column(name = "confirmed")
    private boolean confirmed;

    @NotNull
    @Column(name = "allow_like_comment")
    private boolean allowLikeOrComment;

    @Formula(" (select count(*) from por_comment b where b.confirmed = true and b.post_id = id) ")
    private Long commentsCount;

    @Formula(" (select count(*) from por_like b where b.post_id = id) ")
    private Long likesCount;

}
