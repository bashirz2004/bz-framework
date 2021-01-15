package com.bzamani.framework.controller.portal;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.dto.PostCategoryDto;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.portal.Post;
import com.bzamani.framework.service.portal.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@PreAuthorize("hasRole('16')")
@RestController
@RequestMapping(value = "/rest/portal/post", produces = "application/json;charset=UTF-8")
public class PostController extends BaseController {
    @Autowired
    IPostService iPostService;

    @PreAuthorize("hasRole('17')")
    @PostMapping("/save")
    public Post save(@RequestBody Post post) throws Exception {
        return iPostService.checkAndSave(post);
    }

    @PreAuthorize("hasRole('18')")
    @PostMapping("/confirmPost/{id}")
    public Integer confirmPost(@PathVariable long id) throws Exception {
        return iPostService.confirmPost(id);
    }

    @PreAuthorize("hasRole('17')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) throws Exception {
        return iPostService.checkAndDeleteByEntityId(id);
    }

    @GetMapping("/load/{id}")
    public Post load(@PathVariable("id") long id) {
        return iPostService.loadByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<Post> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iPostService.getAll(sort);
    }

    @GetMapping("/searchPost")
    public Map<String, Object> searchPost(@RequestParam(required = false) String searchBox,
                                          @RequestParam(required = false) Long categoryId,
                                          @RequestParam(required = false) Boolean confirmed,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "8") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iPostService.searchPost(searchBox, categoryId, confirmed, page, size, sort);
    }

    @GetMapping("/getAllUsedPostCategories")
    public List<PostCategoryDto> getAllUsedCategories(@RequestParam(required = false) String searchBox,
                                                      @RequestParam(required = false) Boolean confirmed) {
        return iPostService.getAllUsedPostCategories(searchBox, confirmed);
    }
}
