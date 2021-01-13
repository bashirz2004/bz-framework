package com.bzamani.framework.service.impl.portal;

import com.bzamani.framework.model.portal.Post;
import com.bzamani.framework.repository.portal.IPostRepository;
import com.bzamani.framework.service.core.file.IFileAttachmentService;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.portal.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService extends GenericService<Post, Long> implements IPostService {
    @Autowired
    private IPostRepository iPostRepository;

    @Autowired
    private IFileAttachmentService iFileAttachmentService;

    @Override
    protected JpaRepository<Post, Long> getGenericRepo() {
        return iPostRepository;
    }

    @Override
    @Transactional
    public Post save(Post post) {
        post.setFileCode(post.getFileCode() == null || post.getFileCode().length() == 0 ? null : post.getFileCode());
        String oldFileCode = null;
        String newFileCode = post.getFileCode();
        if (post.getId() != null && post.getId() > 0) //edit mode
            oldFileCode = loadByEntityId(post.getId()).getFileCode();
        iFileAttachmentService.finalizeNewAndDeleteOldAttachment(newFileCode, oldFileCode);
        return super.save(post);
    }

    @Override
    @Transactional
    public boolean deleteByEntityId(Long id) {
        if (loadByEntityId(id).getFileCode() != null)
            iFileAttachmentService.finalizeNewAndDeleteOldAttachment(null, loadByEntityId(id).getFileCode());
        return super.deleteByEntityId(id);
    }

    @Override
    public Map<String, Object> searchPost(String searchBox, Long categoryId, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Post> posts = new ArrayList<Post>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Post> pageTuts = iPostRepository.searchPost(searchBox, categoryId, pagingSort);
        posts = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", posts);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

}
