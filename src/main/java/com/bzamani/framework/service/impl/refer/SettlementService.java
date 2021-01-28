package com.bzamani.framework.service.impl.refer;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferLog;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.model.refer.Settlement;
import com.bzamani.framework.repository.refer.ISettlementRepository;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.refer.IReferLogService;
import com.bzamani.framework.service.refer.IReferService;
import com.bzamani.framework.service.refer.ISettlementService;
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
public class SettlementService extends GenericService<Settlement, Long> implements ISettlementService {
    @Autowired
    private ISettlementRepository iSettlementRepository;

    @Autowired
    private IReferService iReferService;

    @Autowired
    private IReferLogService iReferLogService;

    @Autowired
    private IUserService iUserService;

    @Override
    protected JpaRepository<Settlement, Long> getGenericRepo() {
        return iSettlementRepository;
    }

    @Override
    public Map<String, Object> searchSettlement(String settlementDateShamsiFrom,
                                                String settlementDateShamsiTo,
                                                Long clinicId,
                                                String description,
                                                Boolean confirmed,
                                                Long referId,
                                                int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        List<Settlement> settlements = new ArrayList<>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        Page<Settlement> pageTuts = iSettlementRepository.searchSettlement(settlementDateShamsiFrom,
                settlementDateShamsiTo,
                clinicId,
                description,
                confirmed,
                referId,
                pagingSort);
        settlements = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("entityList", settlements);
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
    public Settlement savSettlement(Settlement settlement) {
        final Long[] medikEarnsReceivedFinalyIfUserNotFilledFirstTime = {0L};
        if (settlement.getId() == null) {
            settlement.setConfirmed(false);
            super.save(settlement);
            List<Refer> newRefers = iReferService.getAllByClinicEqualsAndStatusEqualsAndSettlementEquals(settlement.getClinic(), ReferStatus.finishedWork, null);
            if (newRefers.size() > 0) {
                newRefers.stream().forEach(refer -> {
                    refer.setSettlement(settlement);
                    medikEarnsReceivedFinalyIfUserNotFilledFirstTime[0] += refer.getMedikEarn();
                    iReferService.save(refer);
                });
                if (settlement.getMedikEarnsReceivedFinaly() == null)
                    settlement.setMedikEarnsReceivedFinaly(medikEarnsReceivedFinalyIfUserNotFilledFirstTime[0]);
                return settlement;
            } else
                throw new RuntimeException("این کلینیک هیچ ارجاع تسویه نشده ای ندارد.");
        } else {
            if (loadByEntityId(settlement.getId()).isConfirmed())
                throw new RuntimeException("پس از تایید نهایی دیگر امکان ویرایش اطلاعات وجود ندارد.");
            loadByEntityId(settlement.getId()).setMedikEarnsReceivedFinaly(settlement.getMedikEarnsReceivedFinaly());
            return super.save(settlement);
        }
    }

    @Override
    @Transactional
    public boolean deleteSettlement(long id) {
        if (loadByEntityId(id).isConfirmed())
            throw new RuntimeException("امکان حدف تسویه به دلیل تایید نهایی شدن وجود ندارد.");
        loadByEntityId(id).getRefers().stream().forEach(refer -> {
            iReferService.loadByEntityId(refer.getId()).setSettlement(null);
        });
        return deleteByEntityId(id);
    }

    @Override
    @Transactional
    public long confirm(long id) {
        Settlement settlement = loadByEntityId(id);
        if (settlement.getRefers().size() == 0)
            throw new RuntimeException("امکان تایید نهایی این تسویه حساب به دلیل نبودن هیچ ارجاعی در آن، وجود ندارد. تسویه حساب را حذف کنید و مجددا ثبت نمایید.");
            settlement.getRefers().forEach(refer -> {
                iReferService.loadByEntityId(refer.getId()).setStatus(ReferStatus.settlementDone);
                iReferLogService.save(new ReferLog(null, refer, DateUtility.todayShamsi(), DateUtility.currentTime(),
                        iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getPersonel(),
                        "تسویه حساب با کلینیک", ReferStatus.finishedWork.getPersianTitle(), ReferStatus.settlementDone.getPersianTitle()));
            });
        settlement.setConfirmed(true);
        return save(settlement).getId();
    }
}
