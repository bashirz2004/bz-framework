package com.bzamani.framework.service.impl.clinic;

import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.repository.clinic.IClinicRepository;
import com.bzamani.framework.service.clinic.IClinicService;
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
public class ClinicService extends GenericService<Clinic, Long> implements IClinicService {
    @Autowired
    private IClinicRepository iClinicRepository;

    @Override
    protected JpaRepository<Clinic, Long> getGenericRepo() {
        return iClinicRepository;
    }

    @Override
    public Map<String, Object> searchClinic(String organizationTitle, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Clinic> clinics = new ArrayList<>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Clinic> pageTuts = iClinicRepository.searchClinic(organizationTitle, pagingSort);
        clinics = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", clinics);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalRecords", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else if (direction.equals("desc"))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }
}
