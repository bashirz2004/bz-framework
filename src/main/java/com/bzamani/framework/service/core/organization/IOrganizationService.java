package com.bzamani.framework.service.core.organization;

import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.dto.OrganizationDto;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;
import java.util.Map;

public interface IOrganizationService extends IGenericService<Organization, Long> {

    Map<String, Object> searchOrganization(String title, Boolean active, int page, int size, String[] sort);

    List<Organization> getAllByParentId(Long parentId);

    List<Long> getAllParentIds(Long organizationId);

    List<OrganizationDto> getAuthorizeOrganizationsForUserId(long userId,Long parentId);

    TreeNode getChildrenAsJsonTreeAuthorize(long id);

    boolean userHaveAccessToOrganization(long userId, long organizationId);
}
