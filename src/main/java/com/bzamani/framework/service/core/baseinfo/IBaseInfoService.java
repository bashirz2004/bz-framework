package com.bzamani.framework.service.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;

public interface IBaseInfoService extends IGenericService<BaseInfo, Long> {
    List<BaseInfo> getAllByHeaderId(long headerId);

    List<BaseInfo> getAllByParentId(long parentId);
}
