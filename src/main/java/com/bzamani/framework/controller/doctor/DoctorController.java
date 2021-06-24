package com.bzamani.framework.controller.doctor;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.service.doctor.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/rest/doctor", produces = "application/json;charset=UTF-8")
public class DoctorController extends BaseController {
    @Autowired
    IDoctorService iDoctorService;

    @PreAuthorize("hasRole('1001')")
    @PostMapping("/save")
    public Doctor save(@RequestBody Doctor doctor) {
        return iDoctorService.saveDoctor(doctor);
    }

    @PreAuthorize("hasRole('1001')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iDoctorService.deleteByEntityId(id);
    }

    @GetMapping("/load/{id}")
    public Doctor load(@PathVariable("id") long id) {
        return iDoctorService.loadByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<Doctor> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iDoctorService.getAll(sort);
    }

    @GetMapping("/searchDoctor")
    public Map<String, Object> searchDoctors(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String specialityTitle,
            @RequestParam(required = false) Boolean confirmed,
            @RequestParam(required = false) Boolean showInVipList,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iDoctorService.searchDoctors(firstname, lastname, null, null, null, null,
                null, null, specialityTitle, null, null, null, null, confirmed, showInVipList, page, size, sort);
    }

    @GetMapping("/searchDoctorAuthorize")
    public Map<String, Object> searchDoctorAuthorize(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iDoctorService.searchDoctorAuthorize(firstname, lastname, page, size, sort);
    }
}
