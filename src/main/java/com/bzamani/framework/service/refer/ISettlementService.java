package com.bzamani.framework.service.refer;

import com.bzamani.framework.model.refer.Settlement;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ISettlementService extends IGenericService<Settlement, Long> {
    Map<String, Object> searchSettlement(
            String settlementDateShamsiFrom,
            String settlementDateShamsiTo,
            Long clinicId,
            String description,
            Boolean confirmed,
            Long referId,
            int page,
            int size,
            String[] sort);

    @Transactional
    Settlement savSettlement(Settlement settlement)  ;

    @Transactional
    boolean deleteSettlement(long id);

    @Transactional
    long confirm(long id) ;
}
