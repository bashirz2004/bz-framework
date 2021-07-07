package com.bzamani.framework.controller.homevisit;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.homevisit.HomeVisitRequest;
import com.bzamani.framework.service.homevisit.IHomeVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(value = "/rest/homevisit", produces = "application/json;charset=UTF-8")
public class HomeVisitController extends BaseController {
    @Autowired
    IHomeVisitService iHomeVisitService;

    @PreAuthorize("hasRole('1015')")
    @PostMapping("/save")
    public HomeVisitRequest save(@RequestBody HomeVisitRequest homeVisit) {
        return iHomeVisitService.save(homeVisit);
    }

    @PreAuthorize("hasRole('1015')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iHomeVisitService.deleteByEntityId(id);
    }

    @GetMapping("/load/{id}")
    public HomeVisitRequest load(@PathVariable("id") long id) {
        return iHomeVisitService.loadByEntityId(id);
    }


    @GetMapping("/search")
    public Map<String, Object> search(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) Integer requestType,
            @RequestParam(required = false) Boolean done,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iHomeVisitService.search(firstname, lastname, mobile,requestType, done, page, size, sort);
    }

}
