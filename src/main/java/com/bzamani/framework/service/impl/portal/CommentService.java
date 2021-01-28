package com.bzamani.framework.service.impl.portal;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.portal.Comment;
import com.bzamani.framework.repository.portal.ICommentRepository;
import com.bzamani.framework.service.core.organization.IOrganizationService;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.portal.ICommentService;
import com.bzamani.framework.service.portal.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentService extends GenericService<Comment, Long> implements ICommentService {

    @Autowired
    private ICommentRepository iCommentRepository;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IOrganizationService iOrganizationService;

    @Autowired
    private IPostService iPostService;

    @Override
    protected JpaRepository<Comment, Long> getGenericRepo() {
        return iCommentRepository;
    }

    @Override
    public Map<String, Object> getAllCommentsByPostId(Long postId, Boolean confirmed, int page, int size, String[] sort) {
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
        Page<Comment> pageTuts = iCommentRepository.getAllCommentsByPostId(postId, confirmed, pagingSort);
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
    public Comment saveComment(Comment comment)  {
        if (!iPostService.loadByEntityId(comment.getPost().getId()).isAllowLikeOrComment())
            throw new  RuntimeException("این پست قابلیت ثبت نظر یا لایک را ندارد.");
        Personel commenter = iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getPersonel();
        if (iCommentRepository.findAllByPostEqualsAndCommenterEqualsAndConfirmedEquals(comment.getPost(), commenter, false).size() >= 3)
            throw new  RuntimeException("با تشکر از همراهی شما کاربر گرامی، با توجه به اینکه 3 نظر قبلی شما روی این پست، هنوز تایید نشده است، فعلا امکان ثبت نظر جدید وجود ندارد.");
        comment.setCommenter(commenter);
        comment.setCreateDateShamsi(DateUtility.todayShamsi());
        comment.setConfirmed(false);
        return super.save(comment);
    }

    @Override
    @Transactional
    public Integer changeStatus(long id, boolean status)  {
        checkAccessToOrganization(id);
        return iCommentRepository.changeStatus(id, status, new Date());
    }

    @Override
    @Transactional
    public boolean checkAndDelete(long id)  {
        checkAccessToOrganization(id);
        return super.deleteByEntityId(id);
    }

    public void checkAccessToOrganization(long commentId)  {
        long authenticatedUserId = iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId();
        long authorOrganizationId = loadByEntityId(commentId).getPost().getAuthor().getOrganization().getId();
        if (iOrganizationService.userHaveAccessToOrganization(authenticatedUserId, authorOrganizationId) == false)
            throw new  RuntimeException("شما به واحد سازمانی کاربر ثبت کننده پست دسترسی ندارید.");
    }
}
