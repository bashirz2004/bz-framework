package com.bzamani.framework.repository.portal;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.portal.Comment;
import com.bzamani.framework.model.portal.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query(" FROM Comment e where e.post.id =  CASE WHEN :postId > 0L THEN :postId ELSE e.post.id END " +
            " and e.confirmed = CASE WHEN :confirmed is null THEN e.confirmed ELSE :confirmed END ")
    Page<Comment> getAllCommentsByPostId(@Param("postId") Long postId, @Param("confirmed") Boolean confirmed, Pageable pageable);

    @Query(" FROM Comment e where e.confirmed = true and e.post.id =  CASE WHEN :postId > 0L THEN :postId ELSE e.post.id END ")
    Page<Comment> getAllConfirmedCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

    @Modifying
    @Query("update Comment e set e.confirmed = :status , e.lastUpdateDate = :now where e.id = :id")
    Integer changeStatus(@Param("id") long id, @Param("status") boolean status, @Param("now") Date now);

    List<Comment> findAllByPostEqualsAndCommenterEqualsAndConfirmedEquals(Post post, Personel commenter,Boolean confirmed);

}
