package com.bzamani.framework.service.doctor;

import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.repository.doctor.IDoctorRepository;
import com.bzamani.framework.service.GenericService;
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
public class DoctorService extends GenericService<Doctor, Long> implements IDoctorService {
    @Autowired
    private IDoctorRepository iDoctorRepository;

    @Override
    protected JpaRepository<Doctor, Long> getGenericRepo() {
        return iDoctorRepository;
    }

    @Override
    public Map<String, Object> searchDoctors(String firstname, String lastname, String medicalNationalNumber, Boolean male,
                                             Long stateId, Long cityId, Long regionId, Long specialityId, String specialityTitle, String address,
                                             String telephone, String specialities, String genders, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Doctor> doctors = new ArrayList<Doctor>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Doctor> pageTuts = iDoctorRepository.searchDoctors(firstname, lastname, medicalNationalNumber, male,
                stateId, cityId, regionId, specialityId, specialityTitle, address, telephone, specialities, genders, pagingSort);
        doctors = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", doctors);
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
