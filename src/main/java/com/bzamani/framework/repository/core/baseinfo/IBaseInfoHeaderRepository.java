package com.bzamani.framework.repository.core.baseinfo;

import com.bzamani.framework.model.core.baseinfo.BaseInfoHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBaseInfoHeaderRepository extends JpaRepository<BaseInfoHeader, Long> {

    @Query(" select e from BaseInfoHeader e order by e.title asc ")
    List<BaseInfoHeader> getAllHeaders();
}
