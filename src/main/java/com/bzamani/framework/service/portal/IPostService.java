package com.bzamani.framework.service.portal;

import com.bzamani.framework.dto.PostCategoryDto;
import com.bzamani.framework.model.portal.Post;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IPostService extends IGenericService<Post, Long> {

    @Transactional
    Post checkAndSave(Post post) throws Exception;

    @Transactional
    boolean checkAndDeleteByEntityId(Long id) throws Exception;

    Map<String, Object> searchPost(String searchBox, Long categoryId, Boolean confirmedPost, Boolean confirmedComment, int page, int size, String[] sort);

    List<PostCategoryDto> getAllUsedPostCategories(String searchBox, Boolean confirmed);

    Integer confirmPost(long id) throws Exception;

    Map<String, Object> get4RecentPosts(int page, int size, String[] sort);
}
