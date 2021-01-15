package com.bzamani.framework.service.portal;

import com.bzamani.framework.model.portal.Like;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

public interface ILikeService extends IGenericService<Like, Long> {

    @Transactional
    Like saveLike(Like like) throws Exception;
}
