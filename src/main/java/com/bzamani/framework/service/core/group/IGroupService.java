package com.bzamani.framework.service.core.group;

import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.model.core.group.Group;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IGroupService extends IGenericService<Group, Long> {
    @Transactional
    boolean checkAndDeleteByEntityId(Long id) throws Exception;

    Map<String, Object> searchGroup(String title, int page, int size, String[] sort);

    boolean reSaveGroupActions(long groupId, List<Long> actionIds) throws Exception;

    boolean userHaveAccessToGroup(long userId, long groupId);

    TreeNode getAuthenticatedUserGroupsAsJsonTree();
}
