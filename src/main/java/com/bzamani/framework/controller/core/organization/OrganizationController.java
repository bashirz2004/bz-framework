package com.bzamani.framework.controller.core.organization;

import com.bzamani.framework.common.utility.ParseTree;
import com.bzamani.framework.common.utility.TreeNode;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.service.core.organization.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/core/organization", produces = "application/json;charset=UTF-8")
public class OrganizationController extends BaseController {

    @Autowired
    IOrganizationService iOrganizationService;

    @PostMapping("/save")
    public Organization save(@RequestBody Organization organization) {
        return iOrganizationService.save(organization);
    }

    @GetMapping("/load/{id}")
    public Organization load(@PathVariable("id") long id) {
        return iOrganizationService.loadByEntityId(id);
    }

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

    @GetMapping("/searchOrganization")
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
            treeNode.setIm0("iconText.gif");
            treeNode.setIm1("tombs_open.gif");
            treeNode.setIm2("tombs.gif");
            treeNode.setChildCount(organization.getChildren().size());
            if (lstParrentId.contains(organization.getId())) {
                treeNode = makeChildTreeNode(treeNode, organization, lstParrentId);
            }
            mainTreeNode.getChilds().add(treeNode);
        }
        return mainTreeNode;
    }

}
