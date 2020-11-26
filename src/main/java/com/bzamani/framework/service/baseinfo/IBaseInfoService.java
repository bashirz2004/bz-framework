package com.bzamani.framework.service.baseinfo;

import com.bzamani.framework.model.baseinfo.BaseInfo;
import com.bzamani.framework.service.IGenericService;

import java.util.List;

public interface IBaseInfoService extends IGenericService<BaseInfo, Long> {
    List<BaseInfo> getAllByHeaderId(long headerId);

}
