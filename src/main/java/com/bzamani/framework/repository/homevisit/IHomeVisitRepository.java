package com.bzamani.framework.repository.homevisit;

import com.bzamani.framework.model.doctor.Doctor;
import com.bzamani.framework.model.homevisit.HomeVisitRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IHomeVisitRepository extends JpaRepository<HomeVisitRequest, Long> {
    @Query("SELECT e FROM HomeVisitRequest e where 1 = 1 " +
            " and e.firstname like COALESCE(cast('%'||:firstname||'%' AS text), '%'||e.firstname)||'%'  " +
            " and e.lastname like COALESCE(cast('%'||:lastname||'%' AS text), '%'||e.lastname)||'%' " +
            " and e.mobile like COALESCE(cast('%'||:mobile||'%' AS text), '%'||e.mobile)||'%' " +
            " and e.requestType = case when :requestType >=0 then :requestType else e.requestType end " +
            " and e.done = CASE WHEN :done is null THEN e.done ELSE :done END "
    )
    Page<HomeVisitRequest> search(@Param("firstname") String firstname,
                                  @Param("lastname") String lastname,
                                  @Param("mobile") String mobile,
                                  @Param("requestType") Integer requestType,
                                  @Param("done") Boolean done,
                                  Pageable pageable);


}
