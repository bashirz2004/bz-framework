package com.bzamani.framework.controller.portal;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.portal.Comment;
import com.bzamani.framework.service.portal.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/portal/comment", produces = "application/json;charset=UTF-8")
public class CommentController extends BaseController {
    @Autowired
    private ICommentService iCommentService;

    @PostMapping("/save")
    public Comment save(@RequestBody Comment comment)  {
        return iCommentService.saveComment(comment);
    }

    @PreAuthorize("hasRole('18')")
    @PostMapping("/changeStatus/{id}/{status}")
    public Integer changeStatus(@PathVariable long id, @PathVariable boolean status)  {
        return iCommentService.changeStatus(id, status);
    }

    @PreAuthorize("hasRole('18')")
    @DeleteMapping("/delete/{id}")
    public boolean checkAndDelete(@PathVariable("id") long id)  {
        return iCommentService.checkAndDelete(id);
    }

    @GetMapping("/getAllCommentsByPostId")
    public Map<String, Object> getAllCommentsByPostId(@RequestParam long postId,
                                                      @RequestParam(required = false) Boolean confirmed,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "8") int size,
                                                      @RequestParam(defaultValue = "id,desc") String[] sort) {
        return iCommentService.getAllCommentsByPostId(postId, confirmed, page, size, sort);
    }

}
