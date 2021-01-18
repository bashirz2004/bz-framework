package com.bzamani.framework.repository.doctor;

import com.bzamani.framework.model.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT e FROM Doctor e left join e.personel.state s left join e.personel.city c left join e.personel.region r where 1 = 1  " +
            " and e.personel.firstname like COALESCE(cast('%'||:firstname||'%' AS text), '%'||e.personel.firstname)||'%'  " +
            " and e.personel.lastname like COALESCE(cast('%'||:lastname||'%' AS text), '%'||e.personel.lastname)||'%' " +
            " and case when e.medicalNationalNumber is null then 'foo' else e.medicalNationalNumber end like '%' || coalesce(cast( :medicalNationalNumber as text), case when e.medicalNationalNumber is null then 'foo' else e.medicalNationalNumber end) || '%'" +
            " and e.personel.male = CASE WHEN :male is null THEN e.personel.male ELSE :male END " +
            " and coalesce(s.id,0) =  CASE WHEN :stateId > 0L THEN :stateId ELSE coalesce(s.id,0) END " +
            " and coalesce(c.id,0) = CASE WHEN :cityId > 0L THEN :cityId ELSE coalesce(c.id,0) END " +
            " and coalesce(r.id,0) = CASE WHEN :regionId > 0L THEN :regionId ELSE coalesce(r.id,0) END " +
            " and e.speciality.id = CASE WHEN :specialityId > 0L THEN :specialityId ELSE e.speciality.id END " +
            " and e.speciality.title like COALESCE(cast('%'||:specialityTitle||'%' AS text), '%'||e.speciality.title)||'%'  " +
            " and case when e.personel.address is null then 'foo' else e.personel.address end like '%' || coalesce(cast( :address as text), case when e.personel.address is null then 'foo' else e.personel.address end) || '%'" +
            " and case when e.personel.telephone is null then 'foo' else e.personel.telephone end like '%' || coalesce(cast( :telephone as text), case when e.personel.telephone is null then 'foo' else e.personel.telephone end) || '%'" +
            " and instr(CASE WHEN :genders!='' THEN :genders ELSE cast(e.personel.male as text) END, cast(e.personel.male as text),  1,  1) > 0 " +
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
