package com.bzamani.framework.service.core.organization;

import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.repository.core.organization.IOrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
@Qualifier("")
@Service
public class OrganizationService implements IOrganizationService {

    @Autowired
    IOrganizationRepository iOrganizationRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    @Override
    public Organization create(Organization organization) {
        return iOrganizationRepository.save(organization);
    }

    @Override
    public Organization load(long id) {
        Optional<Organization> organizationData = iOrganizationRepository.findById(id);
        if (organizationData.isPresent())
            return organizationData.get();
        else
            return null;
    }

    @Override
    public Organization update(long id, Organization organization) {
        Optional<Organization> organizationData = iOrganizationRepository.findById(id);
        if (organizationData.isPresent()) {
            Organization _organization = organizationData.get();
            _organization.setTitle(organization.getTitle());
            _organization.setDescription(organization.getDescription());
            _organization.setActive(organization.isActive());
            return iOrganizationRepository.save(_organization);
        } else return null;
    }


    @Override
    public void delete(long id) {
        iOrganizationRepository.deleteById(id);
    }


    @Override
    public List<Organization> getAll(String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return iOrganizationRepository.findAll(Sort.by(orders));
    }


    @Override
    public Map<String, Object> getAllGrid(int page,
                                          int size,
                                          String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        List<Organization> organizations = new ArrayList<Organization>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        Page<Organization> pageTuts = iOrganizationRepository.findAll(pagingSort);
        organizations = pageTuts.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("entityList", organizations);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return response;
    }

/*
    @Override
    public List<Organization> getAllByMyQuery(
            String title,
            String description,
            Boolean active) {
        List<Organization> organizations = new ArrayList<Organization>();
        return iOrganizationRepository.mySearch(title, description, active);
    }*/


    @Override
    public Map<String, Object> getAllGridByMyQuery(
            String title,
            String description,
            Boolean active,
            int page,
            int size,
            String[] sort) {

        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Organization> organizations = new ArrayList<Organization>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Organization> pageTuts;
        pageTuts = iOrganizationRepository.getAllGridByMyQuery(title, description, active, pagingSort);
        organizations = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", organizations);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }
}
