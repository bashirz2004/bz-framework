package com.bzamani.framework.service.impl.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfoHeader;
import com.bzamani.framework.repository.core.baseinfo.IBaseInfoHeaderRepository;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BaseInfoHeaderService  extends GenericService<BaseInfoHeader, Long> implements IBaseInfoHeaderService {
    @Autowired
    IBaseInfoHeaderRepository iBaseInfoHeaderRepository;

    @Override
    protected JpaRepository<BaseInfoHeader, Long> getGenericRepo() {
        return iBaseInfoHeaderRepository;
    }

}
