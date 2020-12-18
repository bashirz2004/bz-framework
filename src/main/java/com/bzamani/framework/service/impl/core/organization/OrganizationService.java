package com.bzamani.framework.service.impl.core.organization;

import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.repository.core.organization.IOrganizationRepository;
import com.bzamani.framework.service.core.organization.IOrganizationService;
import com.bzamani.framework.service.impl.core.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService extends GenericService<Organization, Long> implements IOrganizationService {

    @Autowired
    IOrganizationRepository iOrganizationRepository;

    @Override
    protected JpaRepository<Organization, Long> getGenericRepo() {
        return iOrganizationRepository;
    }

    @Override
    public Map<String, Object> getAllGridByMyQuery(String title, Boolean active, int page, int size, String[] sort) {
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
        pageTuts = iOrganizationRepository.getAllGridByMyQuery(title, active, pagingSort);
        organizations = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", organizations);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    @Override
    public List<Organization> getAllByParentId(Long parentId) {
        if(parentId!=null)
            return iOrganizationRepository.getAllByParentId(parentId);
        else
            return iOrganizationRepository.getRoot();
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

}
