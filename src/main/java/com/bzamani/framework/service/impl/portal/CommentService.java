package com.bzamani.framework.service.impl.portal;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.model.portal.Comment;
import com.bzamani.framework.model.portal.Post;
import com.bzamani.framework.repository.portal.ICommentRepository;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.portal.ICommentService;
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
public class CommentService extends GenericService<Comment, Long> implements ICommentService {

    @Autowired
    private ICommentRepository iCommentRepository;

    @Autowired
    private IUserService iUserService;

    @Override
    protected JpaRepository<Comment, Long> getGenericRepo() {
        return iCommentRepository;
    }

    @Override
    public Map<String, Object> getAllCommentsByPostId(Long postId, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Comment> comments = new ArrayList<>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Comment> pageTuts = iCommentRepository.getAllCommentsByPostId(postId, pagingSort);
        comments = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", comments);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> getAllConfirmedCommentsByPostId(Long postId, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Comment> comments = new ArrayList<>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Comment> pageTuts = iCommentRepository.getAllConfirmedCommentsByPostId(postId, pagingSort);
        comments = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", comments);
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
    @Override
    @Transactional
    public Comment saveComment(Comment comment) throws Exception {
        comment.setCommenter(iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getPersonel());
        comment.setCreateDateShamsi(DateUtility.todayShamsi());
        comment.setConfirmed(false);
        return super.save(comment);
    }
}
