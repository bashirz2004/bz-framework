package com.bzamani.framework.service.impl.core.group;

import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.dto.HierarchicalObjectDto;
import com.bzamani.framework.model.core.action.Action;
import com.bzamani.framework.model.core.group.Group;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.repository.core.group.IGroupRepository;
import com.bzamani.framework.service.core.group.IGroupService;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
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
public class GroupService extends GenericService<Group, Long> implements IGroupService {
    @Autowired
    IGroupRepository iGroupRepository;

    @Autowired
    IUserService iUserService;

    @Override
    protected JpaRepository<Group, Long> getGenericRepo() {
        return iGroupRepository;
    }

    @Transactional
    @Override
    public boolean checkAndDeleteByEntityId(Long id)  {
        if (id == 1L)
            throw new  RuntimeException("گروه کاربری عمومی قابل حذف نمی باشد.");
        else
            return super.deleteByEntityId(id);
    }

    @Override
    @Transactional
    public Group save(Group group) {
        if (group.getId() != null) {
            group.setActions(loadByEntityId(group.getId()).getActions());
            return super.save(group);
        } else {
            super.save(group);
            //karbare sabt konande group bayad khodash be an group dastresi dashte bashad
            User authenticatedUser = iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername());
            authenticatedUser.getGroups().add(group);
            iUserService.saveUserWithSets(authenticatedUser);
            return group;
        }
    }

    @Override
    public Map<String, Object> searchGroup(String title, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Group> groups = new ArrayList<Group>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Group> pageTuts = iGroupRepository.searchGroup(title, pagingSort);
        groups = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", groups);
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
    public boolean reSaveGroupActions(long groupId, List<Long> actionIds)  {
        Group group = loadByEntityId(groupId);
        Set<Action> oldSet = group.getActions();
        oldSet.clear();
        for (long actionId : actionIds) {
            Action a = new Action();
            a.setId(actionId);
            oldSet.add(a);
        }
        group.setActions(oldSet);
        iGroupRepository.save(group);
        return true;
    }

    @Override
    public boolean userHaveAccessToGroup(long userId, long groupId) {
        return iGroupRepository.userHaveAccessToGroup(userId, groupId) > 0 ? true : false;
    }

    @Override
    public TreeNode getAuthenticatedUserGroupsAsJsonTree() {
        TreeNode root = new TreeNode("1", "کاربران عمومی");
        long authenticatedUserId = iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId();
        List<Group> authenticatedUserGroups = iGroupRepository.getAuthorizeGroupsForUserId(authenticatedUserId);
        for (Group item : authenticatedUserGroups) {
            TreeNode node = new TreeNode(String.valueOf(item.getId()), item.getTitle());
            node.setChildCount(0);
            node.addAttr("text", item.getTitle());
            node.addAttr("id", String.valueOf(item.getId()));
            root.getChilds().add(node);
        }
        return root;
    }
}
