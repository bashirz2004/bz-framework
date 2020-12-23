package com.bzamani.framework.repository.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBaseInfoRepository extends JpaRepository<BaseInfo, Long> {
    @Query("from BaseInfo e where e.header.id = :headerId and e.parent is null order by e.code,e.title ")
    List<BaseInfo> getAllByHeaderId(@Param("headerId") long headerId);

    @Query("from BaseInfo e where e.parent.id = :parentId order by e.code,e.title ")
    List<BaseInfo> getAllByParentId(@Param("parentId") long parentId);

    @Query("from BaseInfo e where 1=1 and e.title like COALESCE(cast('%'||:title||'%' AS text), '%'||e.title)||'%'  ")
    Page<BaseInfo> searchBaseinfo(@Param("title") String title, Pageable pageable);


}
