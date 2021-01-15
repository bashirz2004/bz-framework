package com.bzamani.framework.controller.portal;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.portal.Comment;
import com.bzamani.framework.service.portal.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/portal/comment", produces = "application/json;charset=UTF-8")
public class CommentController extends BaseController {
    @Autowired
    ICommentService iCommentService;

    @PostMapping("/save")
    public Comment save(@RequestBody Comment comment) throws Exception {
        return iCommentService.saveComment(comment);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iCommentService.deleteByEntityId(id);
    }
}
