package com.bzamani.framework.controller.core.action;

import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.dto.MenuTreeNodeDto;
import com.bzamani.framework.model.core.action.Action;
import com.bzamani.framework.service.core.action.IActionService;
import com.bzamani.framework.service.core.group.IGroupService;
import com.bzamani.framework.service.core.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/rest/core/action", produces = "application/json;charset=UTF-8")
public class ActionController {

    @Autowired
    IActionService iActionService;

    @Autowired
    IUserService iUserService;

    @Autowired
    IGroupService iGroupService;

    @GetMapping("/loadMenuForCurrentUser")
    public List<Action> loadMenuForCurrentUser()  {
        return iActionService.loadMenuForCurrentUser();
    }

    @GetMapping(value = "/loadCompleteTreeAuthorize")
    public MenuTreeNodeDto loadCompleteTreeAuthorize(long id) {
        return iActionService.loadCompleteTreeAuthorize(id, iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId());
    }

    @GetMapping(value = "/loadWholeTreeWithoutAuthorization")
    public TreeNode loadWholeTreeWithoutAuthorization(long id, long groupId) {
        Set<Action> groupActions = iGroupService.loadByEntityId(groupId).getActions();
        return iActionService.loadWholeTreeWithoutAuthorization(id, groupActions);
    }

}
