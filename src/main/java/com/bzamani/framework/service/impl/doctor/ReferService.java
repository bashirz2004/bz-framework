package com.bzamani.framework.service.impl.doctor;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.repository.doctor.IDoctorRepository;
import com.bzamani.framework.repository.refer.IReferRepository;
import com.bzamani.framework.service.doctor.IDoctorService;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.refer.IReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReferService extends GenericService<Refer, Long> implements IReferService {
    @Autowired
    private IReferRepository iReferRepository;

    @Override
    protected JpaRepository<Refer, Long> getGenericRepo() {
        return iReferRepository;
    }

    @Override
    public Map<String, Object> searchRefer(String referDateShamsiFrom,
                                           String referDateShamsiTo,
                                           Long doctorId,
                                           Long patientId,
                                           Long clinicId,
                                           Long sicknessId,
                                           Long treatmentId,
                                           String receptionDateShamsiFrom,
                                           String receptionDateShamsiTo,
                                           String finishDateShamsiFrom,
                                           String finishDateShamsiTo,
                                           String settlementDateShamsiFrom,
                                           String settlementDateShamsiTo,
                                           ReferStatus status, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Refer> refers = new ArrayList<>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Refer> pageTuts = iReferRepository.searchRefer(referDateShamsiFrom,
                referDateShamsiTo,
                doctorId,
                patientId,
                clinicId,
                sicknessId,
                treatmentId,
                receptionDateShamsiFrom,
                receptionDateShamsiTo,
                finishDateShamsiFrom,
                finishDateShamsiTo,
                settlementDateShamsiFrom,
                settlementDateShamsiTo,
                status, pagingSort);
        refers = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", refers);
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

    @Override
    @Transactional
    public Refer saveRefer(Refer refer) {
        refer.setStatus(ReferStatus.initialSaved);
        return super.save(refer);
    }
}
