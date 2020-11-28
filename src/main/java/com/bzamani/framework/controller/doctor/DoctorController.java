package com.bzamani.framework.controller.doctor;

import com.bzamani.framework.controller.BaseController;
import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.service.doctor.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/doctor", produces = "application/json;charset=UTF-8")
public class DoctorController extends BaseController {
    @Autowired
    IDoctorService iDoctorService;

    @PostMapping("/save")
    public Doctor save(@RequestBody Doctor doctor) {
        return iDoctorService.save(doctor);
    }

    @GetMapping("/load/{id}")
    public Doctor load(@PathVariable("id") long id) {
        return iDoctorService.loadByEntityId(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iDoctorService.deleteByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<Doctor> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iDoctorService.getAll(sort);
    }

    @GetMapping("/searchDoctors")
    public Map<String, Object> searchDoctors(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String medicalNationalNumber,
            @RequestParam(required = false) Boolean male,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long specialityId,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String telephone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iDoctorService.searchDoctors(firstname, lastname,
                medicalNationalNumber, male, stateId, cityId,
                regionId,specialityId, address, telephone, page, size, sort);
    }
}
