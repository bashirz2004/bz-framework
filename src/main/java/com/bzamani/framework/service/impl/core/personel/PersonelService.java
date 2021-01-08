package com.bzamani.framework.service.impl.core.personel;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.repository.core.personel.IPersonelRepository;
import com.bzamani.framework.service.core.file.IFileAttachmentService;
import com.bzamani.framework.service.core.personel.IPersonelService;
import com.bzamani.framework.service.impl.core.GenericService;
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
public class PersonelService extends GenericService<Personel, Long> implements IPersonelService {
    @Autowired
    private IPersonelRepository iPersonelRepository;

    @Autowired
    private IFileAttachmentService iFileAttachmentService;

    @Override
    protected JpaRepository<Personel, Long> getGenericRepo() {
        return iPersonelRepository;
    }

    @Override
    @Transactional
    public Personel save(Personel personel) {
        personel.setFileCode(personel.getFileCode() == null || personel.getFileCode().length() == 0 ? null : personel.getFileCode());
        String oldFileCode = null;
        String newFileCode = personel.getFileCode();
        if (personel.getId() != null && personel.getId() > 0) //edit mode
            oldFileCode = loadByEntityId(personel.getId()).getFileCode();
        iFileAttachmentService.finalizeNewAndDeleteOldAttachment(newFileCode, oldFileCode);
        return super.save(personel);
    }

    @Override
    @Transactional
    public boolean deleteByEntityId(Long id) {
        if (loadByEntityId(id).getFileCode() != null)
            iFileAttachmentService.finalizeNewAndDeleteOldAttachment(null, loadByEntityId(id).getFileCode());
        return super.deleteByEntityId(id);
    }

    @Override
    public Map<String, Object> searchPersonel(String firstname,
                                              String lastname,
                                              String birthCertificateNumber,
                                              String nationalCode,
                                              Boolean male,
                                              String fatherName,
                                              String motherName,
                                              String birthPlace,
                                              Long educationLevelId,
                                              Long militaryServiceStatusId,
                                              String address,
                                              String telephone,
                                              String mobile,
                                              Long organizationId, int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Personel> personels = new ArrayList<Personel>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Personel> pageTuts = iPersonelRepository.searchPersonel(firstname,
                lastname,
                birthCertificateNumber,
                nationalCode,
                male,
                fatherName,
                motherName,
                birthPlace,
                educationLevelId,
                militaryServiceStatusId,
                address,
                telephone,
                mobile,
                organizationId, pagingSort);
        personels = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", personels);
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
    public Personel findByEmailEquals(String email) {
        return iPersonelRepository.findByEmailEquals(email);
    }

    @Override
    public Personel findByMobileEquals(String mobile) {
        return iPersonelRepository.findByMobileEquals(mobile);
    }
}

