package com.bzamani.framework.service.refer;

import com.bzamani.framework.dto.ReferPieChartDto;
import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.model.refer.Settlement;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IReferService extends IGenericService<Refer, Long> {
    Map<String, Object> searchRefer(String referDateShamsiFrom,
                                    String referDateShamsiTo,
                                    String receptionDateShamsiFrom,
                                    String receptionDateShamsiTo,
                                    String finishDateShamsiFrom,
                                    String finishDateShamsiTo,
                                    Long doctorId,
                                    Long patientId,
                                    Long clinicId,
                                    Long id,
                                    Integer status,
                                    int page,
                                    int size,
                                    String[] sort);

    @Transactional
    Refer saveRefer(Refer refer);

    @Transactional
    long changeStatus(long id, ReferStatus newStatus) ;

    @Transactional
    boolean deleteWithLogs(long id) ;

    @Transactional
    Refer finishWork(Refer refer) ;

    List<Refer> getAllByClinicEqualsAndStatusEqualsAndSettlementEquals(Clinic clinic, ReferStatus referStatus , Settlement settlement);

    Map<String, Object> getAllBySettlementId(long settlementId,
                                             Long doctorId,
                                             Long patientId,
                                             Long id,
                                             int page, int size, String[] sort);

    @Transactional
    Refer updateReferSettlementToNull(long referId);

    List<ReferPieChartDto> getAllRefersPercentGroupByStatus();
}
