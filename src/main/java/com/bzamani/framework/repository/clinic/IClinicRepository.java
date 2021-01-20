package com.bzamani.framework.repository.clinic;

import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.model.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IClinicRepository extends JpaRepository<Clinic, Long> {
    @Query("SELECT e FROM Clinic e left join e.organization.state s left join e.organization.city c left join e.organization.region r where 1 = 1  " +
            " and e.organization.title like COALESCE(cast('%'||:organizationTitle||'%' AS text), '%'||e.organization.title)||'%'  " +
            " and e.organization.address like COALESCE(cast('%'||:organizationAddress||'%' AS text), '%'||e.organization.address)||'%'  " +
            " and coalesce(s.id,0) =  CASE WHEN :stateId > 0L THEN :stateId ELSE coalesce(s.id,0) END " +
            " and coalesce(c.id,0) = CASE WHEN :cityId > 0L THEN :cityId ELSE coalesce(c.id,0) END " +
            " and coalesce(r.id,0) = CASE WHEN :regionId > 0L THEN :regionId ELSE coalesce(r.id,0) END ")
    Page<Clinic> searchClinic(@Param("organizationTitle") String organizationTitle, @Param("organizationAddress") String organizationAddress,
                              @Param("stateId") Long stateId, @Param("cityId") Long cityId, @Param("regionId") Long regionId, Pageable pageable);


}
