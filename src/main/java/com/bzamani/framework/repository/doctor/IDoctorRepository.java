package com.bzamani.framework.repository.doctor;

import com.bzamani.framework.model.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT e FROM Doctor e where 1 = 1  " +
            " and e.firstname like COALESCE(cast('%'||:firstname||'%' AS text), '%'||e.firstname)||'%'  " +
            " and e.lastname like COALESCE(cast('%'||:lastname||'%' AS text), '%'||e.lastname)||'%' " +
            " and e.medicalNationalNumber = COALESCE(cast(:medicalNationalNumber AS text), e.medicalNationalNumber) " +
            " and e.male = CASE WHEN :male is null THEN e.male ELSE :male END " +
            " and e.state.id =  CASE WHEN :stateId > 0L THEN :stateId ELSE e.state.id END " +
            " and e.city.id = CASE WHEN :cityId > 0L THEN :cityId ELSE e.city.id END " +
            " and e.region.id = CASE WHEN :regionId > 0L THEN :regionId ELSE e.region.id END " +
            " and e.speciality.id = CASE WHEN :specialityId > 0L THEN :specialityId ELSE e.speciality.id END " +
            " and e.speciality.title like COALESCE(cast('%'||:specialityTitle||'%' AS text), '%'||e.speciality.title)||'%'  " +
            " and e.address like COALESCE(cast('%'||:address||'%' AS text), '%'||e.address)||'%' " +
            " and e.telephone like COALESCE(cast('%'||:telephone||'%' AS text), '%'||e.telephone)||'%' " +
            " and instr(CASE WHEN :genders!='' THEN :genders ELSE cast(e.male as text) END, cast(e.male as text),  1,  1) > 0 " +
            " and instr(CASE WHEN :specialities!='' THEN :specialities ELSE cast(e.speciality.id as text) END, cast(e.speciality.id as text),  1,  1) > 0 "
    )
    Page<Doctor> searchDoctors(@Param("firstname") String firstname,
                               @Param("lastname") String lastname,
                               @Param("medicalNationalNumber") String medicalNationalNumber,
                               @Param("male") Boolean male,
                               @Param("stateId") Long stateId,
                               @Param("cityId") Long cityId,
                               @Param("regionId") Long regionId,
                               @Param("specialityId") Long specialityId,
                               @Param("specialityTitle") String specialityTitle,
                               @Param("address") String address,
                               @Param("telephone") String telephone,
                               @Param("specialities") String specialities,
                               @Param("genders") String genders,
                               Pageable pageable);


}
