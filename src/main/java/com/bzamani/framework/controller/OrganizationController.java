package com.bzamani.framework.controller;

import java.util.*;

import com.bzamani.framework.model.Organization;
import com.bzamani.framework.repository.OrganizationRepository;
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
    OrganizationRepository organizationRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    @PostMapping("/create")
    public Organization create(@RequestBody Organization organization) {
        return organizationRepository.save(organization);
    }

    @GetMapping("/load/{id}")
    public Organization load(@PathVariable("id") long id) {
        Optional<Organization> organizationData = organizationRepository.findById(id);
        if (organizationData.isPresent())
            return organizationData.get();
        else
            return null;
    }

    @PutMapping("/update/{id}")
    public Organization updateOrganization(@PathVariable("id") long id, @RequestBody Organization organization) {
        Optional<Organization> organizationData = organizationRepository.findById(id);
        if (organizationData.isPresent()) {
            Organization _organization = organizationData.get();
            _organization.setTitle(organization.getTitle());
            _organization.setDescription(organization.getDescription());
            _organization.setActive(organization.isActive());
            return organizationRepository.save(_organization);
        } else return null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrganization(@PathVariable("id") long id) {
        organizationRepository.deleteById(id);
    }

    @GetMapping("/getAll")
    public List<Organization> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        List<Order> orders = new ArrayList<Order>();
        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }
        return organizationRepository.findAll(Sort.by(orders));
    }

    @GetMapping("/getAllGrid")
    public Map<String, Object> getAllGrid(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {
        List<Order> orders = new ArrayList<Order>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        List<Organization> organizations = new ArrayList<Organization>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        Page<Organization> pageTuts = organizationRepository.findAll(pagingSort);
        organizations = pageTuts.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("entityList", organizations);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return response;
    }

    @GetMapping("/getAllByMyQuery")
    public List<Organization> getAllByMyQuery(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean active) {
        List<Organization> organizations = new ArrayList<Organization>();
        return organizationRepository.mySearch(title, description, active);
    }

    @GetMapping("/getAllGridByMyQuery")
    public Map<String, Object> getAllGridByMyQuery(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        List<Order> orders = new ArrayList<Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Organization> organizations = new ArrayList<Organization>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Organization> pageTuts;
        pageTuts = organizationRepository.getAllGridByMyQuery(title, description, active, pagingSort);
        organizations = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", organizations);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
}
