package com.bzamani.framework.service.impl.core.organization;

import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.dto.HierarchicalObjectDto;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.repository.core.organization.IOrganizationRepository;
import com.bzamani.framework.service.core.organization.IOrganizationService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService extends GenericService<Organization, Long> implements IOrganizationService {

    @Autowired
    IOrganizationRepository iOrganizationRepository;

    @Autowired
    IUserService iUserService;

    @Override
    protected JpaRepository<Organization, Long> getGenericRepo() {
        return iOrganizationRepository;
    }

    @Override
    public Map<String, Object> searchOrganization(String title, Boolean active, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Organization> organizations = new ArrayList<Organization>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Organization> pageTuts;
        pageTuts = iOrganizationRepository.searchOrganization(title, active, pagingSort);
        organizations = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", organizations);
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
    public List<Organization> getAllByParentId(Long parentId) {
        if (parentId != null)
            return iOrganizationRepository.getAllByParentId(parentId);
        else
            return iOrganizationRepository.getRoot();
    }

    @Override
    public List<Long> getAllParentIds(Long organizationId) {
        return iOrganizationRepository.getAllParentIds(organizationId);
    }

    @Override
    @Transactional
    public boolean deleteByEntityId(Long id) {
        if (id > 1L)
            return super.deleteByEntityId(id);
        else
            return false;
    }

    @Override
    public TreeNode getChildrenAsJsonTreeAuthorize(long id) {
        Organization organization = loadByEntityId(id);
        TreeNode root = new TreeNode(organization.getId().toString(), organization.getTitle());

        for (HierarchicalObjectDto item : iOrganizationRepository.getAuthorizeOrganizationsForUserId(iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId(), organization.getId())) {
            TreeNode node = new TreeNode(String.valueOf(item.getId()), item.getTitle());
            node.setChildCount(item.getChildCount());
            node.addAttr("text", item.getTitle());
            node.addAttr("id", String.valueOf(item.getId()));
            root.getChilds().add(node);
        }

        return root;
    }

    @Override
    public boolean userHaveAccessToOrganization(long userId, long organizationId) {
        return iOrganizationRepository.userHaveAccessToOrganization(userId, organizationId) > 0 ? true : false;
    }

    @Override
    @Transactional
    public Organization save(Organization organization) {
        if (organization.getId() == null) {
            super.save(organization);
            User authenticatedUser = iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername());
            authenticatedUser.getOrganizations().add(organization);
            return organization;
        } else
            return super.save(organization);
    }

}
