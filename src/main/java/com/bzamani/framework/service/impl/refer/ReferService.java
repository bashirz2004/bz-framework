package com.bzamani.framework.service.impl.refer;

import com.bzamani.framework.common.utility.DateUtility;
import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferLog;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.repository.refer.IReferRepository;
import com.bzamani.framework.service.core.file.IFileAttachmentService;
import com.bzamani.framework.service.core.user.IUserService;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.refer.IReferLogService;
import com.bzamani.framework.service.refer.IReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private IReferLogService iReferLogService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileAttachmentService iFileAttachmentService;

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
        refer.setFileCode(refer.getFileCode() == null || refer.getFileCode().length() == 0 ? null : refer.getFileCode());
        String oldFileCode = null;
        String newFileCode = refer.getFileCode();
        if (refer.getId() != null && refer.getId() > 0) //edit mode
            oldFileCode = loadByEntityId(refer.getId()).getFileCode();
        iFileAttachmentService.finalizeNewAndDeleteOldAttachment(newFileCode, oldFileCode);

        Long tempId = refer.getId();
        refer.setStatus(ReferStatus.initialSaved);
        super.save(refer);
        if (tempId == null)
            iReferLogService.save(new ReferLog(null, refer, DateUtility.todayShamsi(), DateUtility.currentTime(),
                    iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getPersonel(),
                    "ثبت", "", ReferStatus.initialSaved.getPersianTitle()));
        return refer;
    }

    @Override
    @Transactional
    public long changeStatus(long id, ReferStatus newStatus) {
        Refer refer = loadByEntityId(id);
        ReferStatus oldStatus = refer.getStatus();
        UserDetails authenticatedUser = SecurityUtility.getAuthenticatedUser();
        Personel authenticatedPersonel = iUserService.findUserByUsernameEquals(authenticatedUser.getUsername()).getPersonel();
        String currentDateShamsi = DateUtility.todayShamsi();
        String currentTime = DateUtility.currentTime();

        switch (newStatus) {
            case referred:
                if (oldStatus == ReferStatus.initialSaved) {
                    refer.setStatus(ReferStatus.referred);
                    iReferLogService.save(new ReferLog(null, refer, currentDateShamsi, currentTime, authenticatedPersonel,
                            "ارجاع به کلینیک", oldStatus.getPersianTitle(), ReferStatus.referred.getPersianTitle()));
                }
                break;
            case initialReception:
                if (oldStatus == ReferStatus.referred) {
                    refer.setStatus(ReferStatus.initialReception);
                    refer.setReceptionDateShamsi(currentDateShamsi);
                    iReferLogService.save(new ReferLog(null, refer, currentDateShamsi, currentTime, authenticatedPersonel,
                            "پذیرش اولیه بیمار", oldStatus.getPersianTitle(), ReferStatus.initialReception.getPersianTitle()));
                }
                break;
            case finishedWork:
                if (oldStatus == ReferStatus.initialReception) {
                    refer.setStatus(ReferStatus.finishedWork);
                    refer.setFinishDateShamsi(currentDateShamsi);
                    iReferLogService.save(new ReferLog(null, refer, currentDateShamsi, currentTime, authenticatedPersonel,
                            "اعلام پایان کار بیمار", oldStatus.getPersianTitle(), ReferStatus.finishedWork.getPersianTitle()));
                }
                break;
            /*case settlementDone:
                if (oldStatus == ReferStatus.finishedWork) {
                    refer.setStatus(ReferStatus.settlementDone);
                    iReferLogService.save(new ReferLog(null, refer, currentDateShamsi, currentTime, authenticatedPersonel,
                            "تسویه حساب با کلینیک", oldStatus.getPersianTitle(), ReferStatus.settlementDone.getPersianTitle()));
                }
                break;*/
            case revoked:
                if (oldStatus != ReferStatus.initialSaved) {
                    refer.setStatus(ReferStatus.revoked);
                    iReferLogService.save(new ReferLog(null, refer, currentDateShamsi, currentTime, authenticatedPersonel,
                            "ابطال ارجاع", oldStatus.getPersianTitle(), ReferStatus.revoked.getPersianTitle()));
                }
                break;
            default:
                break;
        }
        return save(refer).getId();
    }

    @Override
    @Transactional
    public boolean deleteWithLogs(long id) throws Exception {
        Refer refer = loadByEntityId(id);
        if (refer.getStatus() == ReferStatus.initialSaved) {
            List<ReferLog> logs = iReferLogService.findAllByReferEqualsOrderByCreateDateDesc(refer);
            for (ReferLog log : logs)
                iReferLogService.deleteByEntityId(log.getId());
            return super.deleteByEntityId(id);
        } else
            throw new
                    Exception("در این وضعیت، امکان حذف ارجاع وجود ندارد. در صورت داشتن مجوز، می توانید ارجاع را ابطال کنید.");
    }

}
