package com.bzamani.framework.service.baseinfo;

import com.bzamani.framework.model.baseinfo.BaseInfo;
import com.bzamani.framework.repository.baseinfo.IBaseInfoRepository;
import com.bzamani.framework.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseInfoService extends GenericService<BaseInfo, Long> implements IBaseInfoService {
    @Autowired
    IBaseInfoRepository iBaseInfoRepository;

    @Override
    protected JpaRepository<BaseInfo, Long> getGenericRepo() {
        return iBaseInfoRepository;
    }

    @Override
    public List<BaseInfo> getAllByHeaderId(long headerId) {
        return iBaseInfoRepository.getAllByHeaderId(headerId);
    }
}
