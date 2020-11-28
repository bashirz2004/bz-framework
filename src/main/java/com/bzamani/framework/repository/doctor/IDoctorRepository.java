package com.bzamani.framework.repository.doctor;

import com.bzamani.framework.model.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT e FROM Doctor e where 1 = 1  " +
            " and e.firstname like COALESCE(cast(:firstname AS text), e.firstname)||'%'  " +
            " and e.lastname like COALESCE(cast(:lastname AS text), e.lastname)||'%' " +
            " and e.medicalNationalNumber = COALESCE(cast(:medicalNationalNumber AS text), e.medicalNationalNumber) " +
            " and e.male = CASE WHEN :male is null THEN e.male ELSE :male END " +
            " and e.state.id =  CASE WHEN :stateId > 0L THEN :stateId ELSE e.state.id END " +
            " and e.city.id = CASE WHEN :cityId > 0L THEN :cityId ELSE e.city.id END " +
            " and e.region.id = CASE WHEN :regionId > 0L THEN :regionId ELSE e.region.id END " +
            " and e.speciality.id = CASE WHEN :specialityId > 0L THEN :specialityId ELSE e.speciality.id END " +
            " and e.address like COALESCE(cast(:address AS text), e.address)||'%' " +
            " and e.telephone like COALESCE(cast(:telephone AS text), e.telephone)||'%' "
    )
    Page<Doctor> searchDoctors(@Param("firstname") String firstname,
                               @Param("lastname") String lastname,
                               @Param("medicalNationalNumber") String medicalNationalNumber,
                               @Param("male") Boolean male,
                               @Param("stateId") Long stateId,
                               @Param("cityId") Long cityId,
                               @Param("regionId") Long regionId,
                               @Param("specialityId") Long specialityId,
                               @Param("address") String address,
                               @Param("telephone") String telephone, Pageable pageable);


}
