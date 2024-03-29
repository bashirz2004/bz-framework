package com.bzamani.framework.service.impl.core.action;

import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.dto.MenuTreeNodeDto;
import com.bzamani.framework.model.core.action.Action;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.repository.core.action.IActionRepository;
import com.bzamani.framework.service.core.action.IActionService;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ActionService extends GenericService<Action, Long> implements IActionService {

    @Autowired
    IActionRepository iActionRepository;

    @Autowired
    IUserService iUserService;

    @Override
    protected JpaRepository<Action, Long> getGenericRepo() {
        return iActionRepository;
    }

    /*@Override
    public List<Action> loadMenuForCurrentUser() {
        User authenticatedUser = iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername());
        if (authenticatedUser != null)
            return iActionRepository.loadMenuForCurrentUser(authenticatedUser.getId());
        else
            throw new RuntimeException("احراز هویت کاربر جاری به درستی انجام نشده است یا شما به واحد سازمانی خود دسترسی ندارید.");
    }*/

    @Override
    public MenuTreeNodeDto loadCompleteTreeAuthorize(long id, long authenticatedUserId) {
        Action action = loadByEntityId(id);
        MenuTreeNodeDto node = new MenuTreeNodeDto();
        node.setId(id);
        node.setTitle(action.getTitle());
        node.setIconClass(action.getIconClass());
        node.setParentId(action.getParent() != null ? action.getParent().getId() : null);
        node.setSrc(action.getSrc());
        node.setSortOrder(action.getSortOrder());
        List<MenuTreeNodeDto> children = iActionRepository.getAuthorizeActionsForUserId(authenticatedUserId, action.getId()); //get all children that authenticated user has access
        if (children.size() > 0) {
            List<MenuTreeNodeDto> nodeChildren = new ArrayList<>();
            for (MenuTreeNodeDto item : children)
                nodeChildren.add(loadCompleteTreeAuthorize(item.getId(), authenticatedUserId));
            node.setChildren(nodeChildren);
        }
        return node;
    }

    @Override
    public TreeNode loadWholeTreeWithoutAuthorization(long actionId, Set<Action> groupActions) {
        Action action = loadByEntityId(actionId);
        TreeNode treeNode = new TreeNode(action.getId().toString(), action.getTitle());
        List<Action> children = iActionRepository.findActionsByParentOrderBySortOrder(action);
        treeNode.setChildCount(children.size());

        if (groupActions.stream().filter(e -> e.getId() == action.getId()).count() == 1)
            treeNode.addAttr("checked", "1");
        else
            treeNode.addAttr("checked", "0");

        treeNode.addAttr("text", action.getTitle());
        treeNode.addAttr("id", String.valueOf(actionId));
        if (children.size() > 0)
            for (Action item : children)
                treeNode.getChilds().add(loadWholeTreeWithoutAuthorization(item.getId(), groupActions));
        return treeNode;
    }

}
