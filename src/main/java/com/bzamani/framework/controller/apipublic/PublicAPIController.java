package com.bzamani.framework.controller.apipublic;

import com.bzamani.framework.config.security.SecurityUtility;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoService;
import com.bzamani.framework.service.doctor.IDoctorService;
import com.bzamani.framework.service.core.user.IUserService;
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

    @PostMapping("/user/save")
    public User save(@RequestBody User user) {
        return iUserService.save(user);
    }

    @GetMapping("/doctor/load/{id}")
    public Doctor load(@PathVariable("id") long id) {
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
            @RequestParam(defaultValue = "5") int size,
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

    @PostMapping("/user/registerUserByHimself")
    public User registerUserByHimself(@RequestBody User user) {
        return iUserService.registerUserByHimself(user);
    }
}
