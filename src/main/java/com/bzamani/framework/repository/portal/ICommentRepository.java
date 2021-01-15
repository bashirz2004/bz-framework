package com.bzamani.framework.repository.portal;

import com.bzamani.framework.model.portal.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query(" FROM Comment e where e.post.id =  CASE WHEN :postId > 0L THEN :postId ELSE e.post.id END ")
    Page<Comment> getAllCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

    @Query(" FROM Comment e where e.confirmed = true and e.post.id =  CASE WHEN :postId > 0L THEN :postId ELSE e.post.id END ")
    Page<Comment> getAllConfirmedCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

}
