package com.bzamani.framework.service.impl.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.repository.core.baseinfo.IBaseInfoRepository;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoService;
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

    @Override
    public List<BaseInfo> getAllByParentId(long parentId) {
        return iBaseInfoRepository.getAllByParentId(parentId);
    }
}
