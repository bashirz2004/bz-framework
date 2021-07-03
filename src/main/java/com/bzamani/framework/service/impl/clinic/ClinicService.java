package com.bzamani.framework.service.impl.clinic;

import com.bzamani.framework.dto.ClinicSelfRegistrationDto;
import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.repository.clinic.IClinicRepository;
import com.bzamani.framework.service.clinic.IClinicService;
import com.bzamani.framework.service.core.organization.IOrganizationService;
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
public class ClinicService extends GenericService<Clinic, Long> implements IClinicService {
    @Autowired
    private IClinicRepository iClinicRepository;

    @Autowired
    private IOrganizationService iOrganizationService;

    @Override
    protected JpaRepository<Clinic, Long> getGenericRepo() {
        return iClinicRepository;
    }

    @Override
    @Transactional
    public Clinic saveClinic(Clinic clinic) {
        if (clinic.getId() != null && clinic.getId() > 0) {
            Clinic oldClinic = loadByEntityId(clinic.getId());
            if (!oldClinic.getOrganization().getId().equals(clinic.getOrganization().getId()))
                throw new RuntimeException("واحد سازمانی یك كلینیك، قابل ويرایش نیست.");
            if (oldClinic.isConfirmed() != clinic.isConfirmed()) {
                Organization org = iOrganizationService.loadByEntityId(clinic.getOrganization().getId());
                org.setActive(clinic.isConfirmed());
                iOrganizationService.saveOrganization(org);
            }
        }

        return save(clinic);
    }

    @Override
    public Map<String, Object> searchClinic(String organizationTitle, String organizationAddress, Long stateId, Long cityId, Long regionId, Boolean confirmed, Boolean showInVipList, int page, int size, String[] sort) {
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
        Page<Clinic> pageTuts = iClinicRepository.searchClinic(organizationTitle, organizationAddress, stateId, cityId, regionId, confirmed, showInVipList, pagingSort);
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

    @Override
    @Transactional
    public Clinic selfRegister(ClinicSelfRegistrationDto dto) {
        Organization organization = new Organization();
        organization.setActive(false);
        organization.setTitle(dto.getTitle());
        organization.setFileCode(dto.getFileCode());
        organization.setAddress(dto.getAddress());
        organization.setTelephone(dto.getPhone());
        BaseInfo state = new BaseInfo();
        state.setId(dto.getStateId());
        organization.setState(state);
        BaseInfo city = new BaseInfo();
        city.setId(dto.getCityId());
        organization.setCity(city);
        BaseInfo region = new BaseInfo();
        region.setId(dto.getRegionId());
        organization.setRegion(region);
        Organization parent = new Organization();
        parent.setId(1L);
        organization.setParent(parent);
        iOrganizationService.save(organization);

        Clinic clinic = new Clinic();
        clinic.setConfirmed(false);
        clinic.setDiscount(0);
        clinic.setOrganization(organization);
        clinic.setPercent(0);
        clinic.setShowInVipList(false);
        return save(clinic);
    }
}
