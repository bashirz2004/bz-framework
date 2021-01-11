package com.bzamani.framework.controller.core.user;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.dto.SelfUserRegistrationDto;
import com.bzamani.framework.dto.UserGroupDto;
import com.bzamani.framework.dto.UserOrganizationDto;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/core/user", produces = "application/json;charset=UTF-8")
public class UserController extends BaseController {

    @Autowired
    IUserService iUserService;

    @PreAuthorize("hasRole('3')")
    @PostMapping("/save")
    public User save(@RequestBody User user) {
        return iUserService.save(user);
    }

    @PreAuthorize("hasRole('3')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iUserService.deleteByEntityId(id);
    }

    @GetMapping("/load/{id}")
    public User load(@PathVariable("id") long id) {
        return iUserService.loadByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<User> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iUserService.getAll(sort);
    }

    @GetMapping("/searchUser")
    public Map<String, Object> searchUser(@RequestParam(required = false) String firstname,
                                          @RequestParam(required = false) String lastname,
                                          @RequestParam(required = false) String nationalCode,
                                          @RequestParam(required = false) String mobile,
                                          @RequestParam(required = false) Long organizationId,
                                          @RequestParam(required = false) String username,
                                          @RequestParam(required = false) Boolean accountNonExpired,
                                          @RequestParam(required = false) Boolean accountNonLocked,
                                          @RequestParam(required = false) Boolean credentialsNonExpired,
                                          @RequestParam(required = false) Boolean enabled,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "8") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iUserService.searchUser(firstname, lastname, nationalCode, mobile, organizationId, username,
                accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, page, size, sort);
    }

    @PreAuthorize("hasRole('10')")
    @PostMapping("/changePasswordByAdmin")
    public boolean changePasswordByAdmin(@RequestBody SelfUserRegistrationDto dto) {
        return iUserService.changePasswordByAdmin(dto.getUserId(), dto.getPassword());
    }

    @PostMapping("/changeAuthenticatedUserPassword")
    public boolean changeAuthenticatedUserPassword(@RequestBody SelfUserRegistrationDto dto) throws Exception {
        iUserService.changeAuthenticatedUserPassword(dto.getOldPassword(), dto.getPassword());
        return true;
    }

    @GetMapping("/searchUserOrganizations")
    public Map<String, Object> searchUserOrganizations(
            @RequestParam(required = true) long userId,
            @RequestParam(required = false) String organizationTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iUserService.searchUserOrganizations(userId, organizationTitle, page, size, sort);
    }

    @PreAuthorize("hasRole('11')")
    @DeleteMapping("/deleteUserOrganization/{userId}/{organizationId}")
    public boolean deleteUserOrganization(@PathVariable("userId") long userId, @PathVariable("organizationId") long organizationId) throws Exception {
        return iUserService.deleteUserOrganization(userId, organizationId);
    }

    @PreAuthorize("hasRole('11')")
    @PostMapping(value = "/addUserOrganizations")
    public boolean addUserOrganizations(@RequestBody UserOrganizationDto dto) throws Exception {
        return iUserService.addUserOrganizations(dto.getUserId(), dto.getOrganizationIds());
    }

    @GetMapping("/searchUserGroups")
    public Map<String, Object> searchUserGroups(
            @RequestParam(required = true) long userId,
            @RequestParam(required = false) String groupTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iUserService.searchUserGroups(userId, groupTitle, page, size, sort);
    }

    @PreAuthorize("hasRole('12')")
    @DeleteMapping("/deleteUserGroup/{userId}/{groupId}")
    public boolean deleteUserGroup(@PathVariable("userId") long userId, @PathVariable("groupId") long groupId) throws Exception {
        return iUserService.deleteUserGroup(userId, groupId);
    }

    @PreAuthorize("hasRole('12')")
    @PostMapping(value = "/addUserGroups")
    public boolean addUserGroups(@RequestBody UserGroupDto dto) throws Exception {
        return iUserService.addUserGroups(dto.getUserId(), dto.getGroupIds());
    }
}
