package com.bzamani.framework.controller.clinic;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.service.clinic.IClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@PreAuthorize("hasRole('1002')")
@RestController
@RequestMapping(value = "/rest/clinic", produces = "application/json;charset=UTF-8")
public class ClinicController extends BaseController {
    @Autowired
    IClinicService iClinicService;

    @PreAuthorize("hasRole('1003')")
    @PostMapping("/save")
    public Clinic save(@RequestBody Clinic clinic) {
        return iClinicService.saveClinic(clinic);
    }

    @PreAuthorize("hasRole('1003')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iClinicService.deleteByEntityId(id);
    }

    @GetMapping("/load/{id}")
    public Clinic load(@PathVariable("id") long id) {
        return iClinicService.loadByEntityId(id);
    }

    @GetMapping("/searchClinic")
    public Map<String, Object> searchClinic(
            @RequestParam(required = false) String organizationTitle,
            @RequestParam(required = false) Boolean confirmed,
            @RequestParam(required = false) Boolean showInVipList,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iClinicService.searchClinic(organizationTitle, null, -1L, -1L, -1L, confirmed, showInVipList, page, size, sort);
    }
}
