package com.bzamani.framework.service.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;
import java.util.Map;

public interface IBaseInfoService extends IGenericService<BaseInfo, Long> {
    List<BaseInfo> getAllByHeaderId(long headerId);

    List<BaseInfo> getAllByParentId(long parentId);

    String getAllHeadersAsXml();

    String getChildsAsXml(String id);

    Map<String, Object> searchBaseInfo(String title, int page, int size, String[] sort);

    String reMakeTreeAfterAutoComplete(Long id);
}
