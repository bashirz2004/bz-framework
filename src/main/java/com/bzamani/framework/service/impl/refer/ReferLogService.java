package com.bzamani.framework.service.impl.refer;

import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferLog;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.repository.refer.IReferLogRepository;
import com.bzamani.framework.repository.refer.IReferRepository;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.refer.IReferLogService;
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
public class ReferLogService extends GenericService<ReferLog, Long> implements IReferLogService {
    @Autowired
    private IReferLogRepository iReferLogRepository;

    @Override
    protected JpaRepository<ReferLog, Long> getGenericRepo() {
        return iReferLogRepository;
    }

    @Override
    public List<ReferLog> findAllByReferEqualsOrderByCreateDateDesc(Refer refer) {
        return iReferLogRepository.findAllByReferEqualsOrderByCreateDateDesc(refer);
    }

}
