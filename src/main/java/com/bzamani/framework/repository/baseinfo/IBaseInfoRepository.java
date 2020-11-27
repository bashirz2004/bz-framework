package com.bzamani.framework.repository.baseinfo;

import com.bzamani.framework.model.baseinfo.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBaseInfoRepository extends JpaRepository<BaseInfo, Long> {
    @Query("SELECT e FROM BaseInfo e where e.parent is null and e.header.id = :headerId ")
    List<BaseInfo> getAllByHeaderId(@Param("headerId") long headerId);

    @Query("SELECT e FROM BaseInfo e where e.parent.id = :parentId ")
    List<BaseInfo> getAllByParentId(@Param("parentId") long parentId);

}
