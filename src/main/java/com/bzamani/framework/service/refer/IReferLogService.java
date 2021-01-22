package com.bzamani.framework.service.refer;

import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferLog;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;

public interface IReferLogService extends IGenericService<ReferLog, Long> {
    List<ReferLog> findAllByReferEqualsOrderByCreateDateDesc(Refer refer);
}
