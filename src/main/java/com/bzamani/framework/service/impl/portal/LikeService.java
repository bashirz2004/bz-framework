package com.bzamani.framework.service.impl.portal;

import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.model.portal.Like;
import com.bzamani.framework.repository.portal.ILikeRepository;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.portal.ILikeService;
import com.bzamani.framework.service.portal.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService extends GenericService<Like, Long> implements ILikeService {

    @Autowired
    private ILikeRepository iLikeRepository;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IPostService iPostService;

    @Override
    protected JpaRepository<Like, Long> getGenericRepo() {
        return iLikeRepository;
    }

    @Override
    @Transactional
    public Like saveLike(Like like)  {
        if(iPostService.loadByEntityId(like.getPost().getId()).isAllowLikeOrComment())
            like.setLiker(iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getPersonel());
        else
            throw new  RuntimeException("این پست قابلیت ثبت نظر یا لایک را تدارد.");
        return super.save(like);
    }
}
