package com.bzamani.framework.controller.refer;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.service.clinic.IClinicService;
import com.bzamani.framework.service.doctor.IDoctorService;
import com.bzamani.framework.service.refer.IReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/refer", produces = "application/json;charset=UTF-8")
public class ReferController extends BaseController {
    @Autowired
    IReferService iReferService;

    @Autowired
    IDoctorService iDoctorService;

    @PreAuthorize("hasRole('1005')")
    @PostMapping("/save")
    public Refer save(@RequestBody Refer refer) {
        return iReferService.saveRefer(refer);
    }

    @PreAuthorize("hasRole('1005')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) throws Exception {
        return iReferService.deleteWithLogs(id);
    }

    @GetMapping("/load/{id}")
    public Refer load(@PathVariable("id") long id) {
        return iReferService.loadByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<Refer> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iReferService.getAll(sort);
    }

    @GetMapping("/searchRefer")
    public Map<String, Object> searchRefer(
            @RequestParam(required = false) String referDateShamsiFrom,
            @RequestParam(required = false) String referDateShamsiTo,
            @RequestParam(required = false) String receptionDateShamsiFrom,
            @RequestParam(required = false) String receptionDateShamsiTo,
            @RequestParam(required = false) String finishDateShamsiFrom,
            @RequestParam(required = false) String finishDateShamsiTo,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long clinicId,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iReferService.searchRefer(referDateShamsiFrom, referDateShamsiTo, receptionDateShamsiFrom, receptionDateShamsiTo, finishDateShamsiFrom,
                finishDateShamsiTo, doctorId,patientId, clinicId, id, status, page, size, sort);
    }

    @GetMapping("/getAuthenticatedDoctor")
    public Doctor getAuthenticatedDoctor() {
        return iDoctorService.getAuthenticatedDoctor();
    }

    @PostMapping("/changeStatus/{id}/{newStatus}")
    public Long changeStatus(@PathVariable long id, @PathVariable ReferStatus newStatus) throws Exception {
        return iReferService.changeStatus(id, newStatus);
    }

    @PreAuthorize("hasRole('1008')")
    @PostMapping("/finishWork")
    public Refer finishWork(@RequestBody Refer refer) throws Exception {
        return iReferService.finishWork(refer);
    }

    @GetMapping("/getClinicPercent/{referId}")
    public Integer getClinicPercent(@PathVariable long referId) {
        return load(referId).getClinic().getPercent();
    }
}
