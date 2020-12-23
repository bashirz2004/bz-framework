package com.bzamani.framework.service.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfoHeader;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;

public interface IBaseInfoHeaderService extends IGenericService<BaseInfoHeader, Long> {
    List<BaseInfoHeader> getAllHeaders();
}
