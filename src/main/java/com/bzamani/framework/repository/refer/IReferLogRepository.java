package com.bzamani.framework.repository.refer;

import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReferLogRepository extends JpaRepository<ReferLog, Long> {
    List<ReferLog> findAllByReferEqualsOrderByCreateDateDesc(Refer refer);


}
