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
            " and e.organization.title like COALESCE(cast('%'||:organizationTitle||'%' AS text), '%'||e.organization.title)||'%'  ")
    Page<Clinic> searchClinic(@Param("organizationTitle") String organizationTitle, Pageable pageable);


}
