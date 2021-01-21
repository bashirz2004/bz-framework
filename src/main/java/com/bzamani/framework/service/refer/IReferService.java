package com.bzamani.framework.service.refer;

import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface IReferService extends IGenericService<Refer, Long> {
    Map<String, Object> searchRefer(String referDateShamsiFrom,
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
                                    ReferStatus status, int page, int size, String[] sort);

    @Transactional
    Refer saveRefer(Refer refer);
}
