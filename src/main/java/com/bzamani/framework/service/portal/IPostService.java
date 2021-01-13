package com.bzamani.framework.service.portal;

import com.bzamani.framework.model.portal.Post;
import com.bzamani.framework.service.core.IGenericService;

import java.util.Map;

public interface IPostService extends IGenericService<Post, Long> {

    Map<String, Object> searchPost(String searchBox, Long categoryId, int page, int size, String[] sort);

}
