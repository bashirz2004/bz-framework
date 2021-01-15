package com.bzamani.framework.service.portal;

import com.bzamani.framework.model.portal.Comment;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ICommentService extends IGenericService<Comment, Long> {

    Map<String, Object> getAllCommentsByPostId(Long postId, int page, int size, String[] sort);

    Map<String, Object> getAllConfirmedCommentsByPostId(Long postId, int page, int size, String[] sort);

    @Transactional
    Comment saveComment(Comment comment) throws Exception;
}
