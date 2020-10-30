package com.bzamani.framework.controller.core.organization;

import java.util.*;

import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.repository.core.organization.IOrganizationRepository;
import com.bzamani.framework.service.core.organization.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/core/organization")
public class OrganizationController {

    @Autowired
    IOrganizationService iOrganizationService;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    @PostMapping("/create")
    public Organization create(@RequestBody Organization organization) {
        return iOrganizationService.create(organization);
    }

    @GetMapping("/load/{id}")
    public Organization load(@PathVariable("id") long id) {
        return iOrganizationService.load(id);
    }

    @PutMapping("/update/{id}")
    public Organization updateOrganization(@PathVariable("id") long id, @RequestBody Organization organization) {
        return iOrganizationService.update(id, organization);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrganization(@PathVariable("id") long id) {
        iOrganizationService.delete(id);
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

    @GetMapping("/getAllGridByMyQuery")
    public Map<String, Object> getAllGridByMyQuery(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iOrganizationService.getAllGridByMyQuery(title, description, active, page, size, sort);
    }
}
