package com.bzamani.framework.service.core.action;

import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.dto.MenuTreeNodeDto;
import com.bzamani.framework.model.core.action.Action;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;
import java.util.Set;

public interface IActionService extends IGenericService<Action, Long> {
    //List<Action> loadMenuForCurrentUser() ;

    TreeNode loadWholeTreeWithoutAuthorization(long actionId, Set<Action> groupActions);

    MenuTreeNodeDto loadCompleteTreeAuthorize(long id, long authenticatedUserId);
}
