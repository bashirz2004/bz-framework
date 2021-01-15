package com.bzamani.framework.controller.portal;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.portal.Like;
import com.bzamani.framework.service.portal.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/portal/like", produces = "application/json;charset=UTF-8")
public class LikeController extends BaseController {
    @Autowired
    ILikeService iLikeService;

    @PostMapping("/save")
    public Like saveLike(@RequestBody Like like) throws Exception {
        return iLikeService.saveLike(like);
    }

}
