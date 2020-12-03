package com.bzamani.framework.service.baseinfo;

import com.bzamani.framework.model.baseinfo.BaseInfoHeader;
import com.bzamani.framework.repository.baseinfo.IBaseInfoHeaderRepository;
import com.bzamani.framework.service.GenericService;
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
