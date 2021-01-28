package com.bzamani.framework.controller.apipublic;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.dto.PostCategoryDto;
import com.bzamani.framework.dto.SelfUserRegistrationDto;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.model.portal.Comment;
import com.bzamani.framework.model.portal.Post;
import com.bzamani.framework.service.clinic.IClinicService;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoService;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.doctor.IDoctorService;
import com.bzamani.framework.service.portal.ICommentService;
import com.bzamani.framework.service.portal.IPostService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/public", produces = "application/json;charset=UTF-8")
public class PublicAPIController extends BaseController {
    @Autowired
    IUserService iUserService;

    @Autowired
    IDoctorService iDoctorService;

    @Autowired
    IBaseInfoService iBaseInfoService;

    @Autowired
    IPostService iPostService;

    @Autowired
    ICommentService iCommentService;

    @Autowired
    IClinicService iClinicService;

    @GetMapping(value = "/getCurrentDateShamsi", produces = "text/plain;charset=UTF-8")
    public String getCurrentDateShamsi() {
        return DateUtility.todayShamsi();
    }

    @GetMapping("/doctor/load/{id}")
    public Doctor loadDoctor(@PathVariable("id") long id) {
        return iDoctorService.loadByEntityId(id);
    }

    @GetMapping("doctor/searchDoctors")
    public Map<String, Object> searchDoctors(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String medicalNationalNumber,
            @RequestParam(required = false) Boolean male,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long specialityId,
            @RequestParam(required = false) String specialityTitle,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String telephone,
            @RequestParam(required = false) String specialities,
            @RequestParam(required = false) String genders,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iDoctorService.searchDoctors(firstname, lastname,
                medicalNationalNumber, male, stateId, cityId,
                regionId, specialityId, specialityTitle, address, telephone, specialities, genders, page, size, sort);
    }

    @GetMapping("/baseinfo/getAllByHeaderId/{headerId}")
    public List<BaseInfo> getAllByHeaderId(@PathVariable long headerId) {
        return iBaseInfoService.getAllByHeaderId(headerId);
    }

    @GetMapping("/baseinfo/getAllByParentId/{parentId}")
    public List<BaseInfo> getAllByParentId(@PathVariable long parentId) {
        return iBaseInfoService.getAllByParentId(parentId);
    }

    @GetMapping("/user/getAuthenticatedUser")
    public User getAuthenticatedUser() {
        return iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername());
    }

    @PostMapping("/user/selfRegister")
    public User selfRegister(@RequestBody SelfUserRegistrationDto userDto)  {
        return iUserService.selfRegister(userDto);
    }

    @PostMapping("/user/sendPasswordToUserEmail")
    public void sendPasswordToUserEmail(@RequestParam String email)  {
        iUserService.sendPasswordToUserEmail(email);
    }

    @GetMapping(value = "/user/getUserNewPassword", produces = "text/plain;charset=UTF-8")
    public String getUserNewPassword(@RequestParam String mobile)  {
        return iUserService.updatePasswordOfUserByMobile(mobile, RandomStringUtils.random(5, true, true));
    }

    @GetMapping("/portal/post/searchPost")
    public Map<String, Object> searchPost(@RequestParam(required = false) String searchBox,
                                          @RequestParam(required = false) Long categoryId,
                                          @RequestParam(required = false) Boolean confirmedPost,
                                          @RequestParam(required = false) Boolean confirmedComment,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "8") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iPostService.searchPost(searchBox, categoryId, confirmedPost, confirmedComment, page, size, sort);
    }

    @GetMapping("/portal/post/get4RecentPosts")
    public Map<String, Object> get4RecentPosts(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "4") int size,
                                               @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iPostService.get4RecentPosts(page, size, sort);
    }

    @GetMapping("/portal/post/getAllUsedPostCategories")
    public List<PostCategoryDto> getAllUsedCategories(@RequestParam(required = false) String searchBox,
                                                      @RequestParam(required = false) Boolean confirmed) {
        return iPostService.getAllUsedPostCategories(searchBox, confirmed);
    }

    @GetMapping("/portal/post/load/{id}")
    public Post loadPost(@PathVariable("id") long id) {
        return iPostService.loadByEntityId(id);
    }

    @GetMapping("/portal/comment/getAllConfirmedCommentsByPostId")
    public Map<String, Object> getAllConfirmedCommentsByPostId(@RequestParam(required = false) Long postId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "8") int size,
                                                               @RequestParam(defaultValue = "id,desc") String[] sort) {
        return iCommentService.getAllConfirmedCommentsByPostId(postId, page, size, sort);
    }

    @GetMapping("clinic/searchClinic")
    public Map<String, Object> searchClinic(
            @RequestParam(required = false) String organizationTitle,
            @RequestParam(required = false) String organizationAddress,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iClinicService.searchClinic(organizationTitle, organizationAddress, stateId, cityId, regionId, page, size, sort);
    }

}
