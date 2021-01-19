package com.bzamani.framework.controller.core.organization;

import com.bzamani.framework.common.utility.ParseTree;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.service.core.organization.IOrganizationService;
import com.bzamani.framework.service.core.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/core/organization", produces = "application/json;charset=UTF-8")
public class OrganizationController extends BaseController {

    @Autowired
    IOrganizationService iOrganizationService;

    @Autowired
    IUserService iUserService;

    @PreAuthorize("hasRole('7')")
    @PostMapping("/save")
    public Organization save(@RequestBody Organization organization) {
        return iOrganizationService.saveOrganization(organization);
    }

    @GetMapping("/load/{id}")
    public Organization load(@PathVariable("id") long id) {
        return iOrganizationService.loadByEntityId(id);
    }

    @PreAuthorize("hasRole('7')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iOrganizationService.deleteByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<Organization> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iOrganizationService.getAll(sort);
    }

    @GetMapping("/getAllGrid")
    public Map<String, Object> getAllGrid(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {
        return iOrganizationService.getAllGrid(page, size, sort);
    }

    @GetMapping("/searchOrganizationAutoComplete")
    public Map<String, Object> searchOrganization(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iOrganizationService.searchOrganization(title, active, page, size, sort);
    }

    @GetMapping("/getAllByParentId")
    public List<Organization> getAllByParentId(@RequestParam String id) {
        return iOrganizationService.getAllByParentId(id.equals("#") ? null : Long.valueOf(id));
    }

    @GetMapping(value = "/getChildrenAsString/{id}", produces = "text/plain;charset=UTF-8")
    public String getAllByParentId(@PathVariable Long id) {
        return ParseTree.createTreeXML(makeTree(id));
    }

    public TreeNode makeTree(Long id) {
        List<Long> lstParrentId = new ArrayList<Long>();
        Organization org = new Organization();
        lstParrentId.add(id);
        org.setId(id);
        TreeNode mainTreeNode = new TreeNode();
        return makeChildTreeNode(mainTreeNode, org, lstParrentId);
    }

    public TreeNode makeChildTreeNode(TreeNode mainTreeNode, Organization org, List<Long> lstParrentId) {
        TreeNode treeNode = null;
        List<Organization> lstPower;

        if (org.getId() == 0) {
            lstPower = new ArrayList<Organization>(iOrganizationService.getAllByParentId(null));
        } else {
            lstPower = new ArrayList<Organization>(iOrganizationService.getAllByParentId(org.getId()));
        }

        for (Organization organization : lstPower) {
            treeNode = new TreeNode(organization.getId().toString(), organization.getTitle());
            treeNode.setIm0("leaf.gif");
            treeNode.setIm1("folderOpen.gif");
            treeNode.setIm2("folderClosed.gif");
            treeNode.setChildCount(organization.getChildren().size());
            if (lstParrentId.contains(organization.getId())) {
                treeNode = makeChildTreeNode(treeNode, organization, lstParrentId);
            }
            mainTreeNode.getChilds().add(treeNode);
        }
        return mainTreeNode;
    }

    @GetMapping(value = "/searchOrganizationTree/{organizationId}", produces = "text/plain;charset=UTF-8")
    public String searchOrganizationTree(@PathVariable long organizationId) {
        StringBuffer result = new StringBuffer("");
        if (organizationId == 1) {
            return getAllByParentId(organizationId);
        } else {
            List<Long> organIds = iOrganizationService.getAllParentIds(organizationId);
            for (Long currentOrgan : organIds) {
                Organization org = iOrganizationService.loadByEntityId(currentOrgan);
                if (currentOrgan == 1) {
                    makeTreeString(org, result);
                } else {
                    String path = "<item text=\"...\" im0=\"leaf.gif\" id=\"t" + currentOrgan + "\"/>";
                    result = new StringBuffer(result.toString().replace(path, makeTreeForSearchResult(org)));
                }
            }
            return result.toString();
        }
    }

    public void makeTreeString(Organization organization, StringBuffer returnValue) {
        List<Organization> children = new ArrayList<Organization>(organization.getChildren());
        String im0 = "leaf.gif";
        String im1 = "folderOpen.gif";
        String im2 = "folderClosed.gif";

        for (Organization org : children) {
            returnValue.append("<item     text=\"" + org.getTitle() + "\" id=\"" + org.getId() + "\"   im0=\"" + im0 + "\" im1=\"" + im1 + "\" im2=\"" + im2 + "\"");
            if (org.getChildren().size() > 0) {
                returnValue.append(">");
                returnValue.append("<item text=\"...\" im0=\"leaf.gif\" id=\"t" + org.getId() + "\"/>");
                returnValue.append("</item>");
            } else {
                returnValue.append("/>");
            }
        }
    }

    public StringBuffer makeTreeForSearchResult(Organization organization) {
        StringBuffer returnVal = new StringBuffer();
        List<Organization> children = new ArrayList<Organization>(organization.getChildren());
        String im0 = "leaf.gif";
        String im1 = "folderOpen.gif";
        String im2 = "folderClosed.gif";

        for (Organization org : children) {

            returnVal.append("<item     text=\"" + org.getTitle() + "\" id=\"" + org.getId() + "\"   im0=\"" + im0 + "\" im1=\"" + im1 + "\" im2=\"" + im2 + "\"");
            if (org.getChildren().size() > 0) {
                returnVal.append(">");
                returnVal.append("<item text=\"...\" im0=\"leaf.gif\" id=\"t" + org.getId() + "\"/>");
                returnVal.append("</item>");
            } else {
                returnVal.append("/>");
            }
        }
        return returnVal;
    }

    @GetMapping(value = "/getChildrenAsJsonTreeAuthorize")
    public TreeNode getChildrenAsJsonTreeAuthorize(long id) {
        return iOrganizationService.getChildrenAsJsonTreeAuthorize(id);
    }

    @GetMapping(value = "/authenticatedUserHaveAccessToOrganization")
    public boolean authenticatedUserHaveAccessToOrganization(long organizationId) {
        return iOrganizationService.userHaveAccessToOrganization(
                iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId(), organizationId);

    }


}
