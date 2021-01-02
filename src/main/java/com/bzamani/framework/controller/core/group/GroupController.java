package com.bzamani.framework.controller.core.group;

import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.dto.GroupActionDto;
import com.bzamani.framework.model.core.group.Group;
import com.bzamani.framework.service.core.group.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@PreAuthorize("hasRole('13')")
@RestController
@RequestMapping(value = "/rest/core/group", produces = "application/json;charset=UTF-8")
public class GroupController extends BaseController {
    @Autowired
    IGroupService iGroupService;

    @PreAuthorize("hasRole('14')")
    @PostMapping("/save")
    public Group save(@RequestBody Group group) {
        return iGroupService.save(group);
    }

    @PreAuthorize("hasRole('14')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) throws Exception {
        return iGroupService.checkAndDeleteByEntityId(id);
    }

    @GetMapping("/load/{id}")
    public Group load(@PathVariable("id") long id) {
        return iGroupService.loadByEntityId(id);
    }

    @GetMapping("/searchGroups")
    public Map<String, Object> searchGroups(@RequestParam(required = false) String title,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "8") int size,
                                            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iGroupService.searchGroup(title, page, size, sort);
    }

    @PreAuthorize("hasRole('15')")
    @PostMapping(value = "/reSaveGroupActions")
    public boolean reSaveGroupActions(@RequestBody GroupActionDto dto) throws Exception {
        return iGroupService.reSaveGroupActions(dto.getGroupId(), dto.getActionIds());
    }

    @GetMapping(value = "/getAuthenticatedUserGroupsAsJsonTree")
    public TreeNode getAuthenticatedUserGroupsAsJsonTree() {
        return iGroupService.getAuthenticatedUserGroupsAsJsonTree();
    }
}
