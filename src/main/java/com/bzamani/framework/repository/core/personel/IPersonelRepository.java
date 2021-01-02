package com.bzamani.framework.repository.core.personel;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonelRepository extends JpaRepository<Personel, Long> {
    @Query("SELECT e FROM Personel e where 1 = 1  " +
            " and e.firstname like COALESCE(cast('%'||:firstname||'%' AS text), '%'||e.firstname)||'%'  " +
            " and e.lastname like COALESCE(cast('%'||:lastname||'%' AS text), '%'||e.lastname)||'%' " +
            " and case when e.birthCertificateNumber is null then 'foo' else e.birthCertificateNumber end like '%' || coalesce(cast( :birthCertificateNumber as text), case when e.birthCertificateNumber is null then 'foo' else e.birthCertificateNumber end) || '%'" +
            " and case when e.nationalCode is null then 'foo' else e.nationalCode end like '%' || coalesce(cast( :nationalCode as text), case when e.nationalCode is null then 'foo' else e.nationalCode end) || '%'" +
            " and e.male = CASE WHEN :male is null THEN e.male ELSE :male END " +
            " and case when e.fatherName is null then 'foo' else e.fatherName end like '%' || coalesce(cast( :fatherName as text), case when e.fatherName is null then 'foo' else e.fatherName end) || '%'" +
            " and case when e.motherName is null then 'foo' else e.motherName end like '%' || coalesce(cast( :motherName as text), case when e.motherName is null then 'foo' else e.motherName end) || '%'" +
            " and case when e.birthPlace is null then 'foo' else e.birthPlace end like '%' || coalesce(cast( :birthPlace as text), case when e.birthPlace is null then 'foo' else e.birthPlace end) || '%'" +
            " and case when e.educationLevel is null then 1 else e.educationLevel.id end = CASE WHEN :educationLevelId > 0L THEN :educationLevelId ELSE case when e.educationLevel is null then 1 else e.educationLevel.id end END " +
            " and case when e.militaryServiceStatus is null then 1 else e.militaryServiceStatus.id end = CASE WHEN :militaryServiceStatusId > 0L THEN :militaryServiceStatusId ELSE case when e.militaryServiceStatus is null then 1 else e.militaryServiceStatus.id end END " +
            " and case when e.address is null then 'foo' else e.address end like '%' || coalesce(cast( :address as text), case when e.address is null then 'foo' else e.address end) || '%'" +
            " and case when e.telephone is null then 'foo' else e.telephone end like '%' || coalesce(cast( :telephone as text), case when e.telephone is null then 'foo' else e.telephone end)" +
            " and case when e.mobile is null then 'foo' else e.mobile end like '%' || coalesce(cast( :mobile as text), case when e.mobile is null then 'foo' else e.mobile end)" +
            " and e.organization.id =  CASE WHEN :organizationId > 0L THEN :organizationId ELSE e.organization.id END ")
    Page<Personel> searchPersonel(@Param("firstname") String firstname,
                                  @Param("lastname") String lastname,
                                  @Param("birthCertificateNumber") String birthCertificateNumber,
                                  @Param("nationalCode") String nationalCode,
                                  @Param("male") Boolean male,
                                  @Param("fatherName") String fatherName,
                                  @Param("motherName") String motherName,
                                  @Param("birthPlace") String birthPlace,
                                  @Param("educationLevelId") Long educationLevelId,
                                  @Param("militaryServiceStatusId") Long militaryServiceStatusId,
                                  @Param("address") String address,
                                  @Param("telephone") String telephone,
                                  @Param("mobile") String mobile,
                                  @Param("organizationId") Long organizationId,
                                  Pageable pageable);
    Personel findByEmailEquals(String email);


}
