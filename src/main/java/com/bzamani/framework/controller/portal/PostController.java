package com.bzamani.framework.controller.portal;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.portal.Post;
import com.bzamani.framework.service.portal.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('16')")
@RestController
@RequestMapping(value = "/rest/portal/post", produces = "application/json;charset=UTF-8")
public class PostController extends BaseController {
    @Autowired
    IPostService iPostService;

    @PreAuthorize("hasRole('17')")
    @PostMapping("/save")
    public Post save(@RequestBody Post post)  {
        return iPostService.checkAndSave(post);
    }

    @PreAuthorize("hasRole('18')")
    @PostMapping("/confirmPost/{id}")
    public Integer confirmPost(@PathVariable long id)  {
        return iPostService.confirmPost(id);
    }

    @PreAuthorize("hasRole('17')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id)  {
        return iPostService.checkAndDeleteByEntityId(id);
    }

}
