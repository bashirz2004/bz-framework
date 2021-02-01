package com.bzamani.framework.repository.refer;

import com.bzamani.framework.dto.ReferChartDto;
import com.bzamani.framework.dto.ReferPieChartDto;
import com.bzamani.framework.model.clinic.Clinic;
import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferStatus;
import com.bzamani.framework.model.refer.Settlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReferRepository extends JpaRepository<Refer, Long> {
    @Query("SELECT e FROM Refer e where 1 = 1 " +
            " and coalesce(e.referDateShamsi , '') >=  case when :referDateShamsiFrom = '' then coalesce(e.referDateShamsi , '') else :referDateShamsiFrom end " +
            " and coalesce(e.referDateShamsi , '') <=  case when :referDateShamsiTo = '' then coalesce(e.referDateShamsi , '') else :referDateShamsiTo end " +
            " and coalesce(e.receptionDateShamsi , '') >=  case when :receptionDateShamsiFrom = '' then coalesce(e.receptionDateShamsi , '') else :receptionDateShamsiFrom end " +
            " and coalesce(e.receptionDateShamsi , '') <=  case when :receptionDateShamsiTo = '' then coalesce(e.receptionDateShamsi , '') else :receptionDateShamsiTo end " +
            " and coalesce(e.finishDateShamsi , '') >=  case when :finishDateShamsiFrom = '' then coalesce(e.finishDateShamsi , '') else :finishDateShamsiFrom end " +
            " and coalesce(e.finishDateShamsi , '') <=  case when :finishDateShamsiTo = '' then coalesce(e.finishDateShamsi , '') else :finishDateShamsiTo end " +
            " and e.doctor.id =  CASE WHEN :doctorId > 0L THEN :doctorId ELSE e.doctor.id END " +
            " and e.patient.id =  CASE WHEN :patientId > 0L THEN :patientId ELSE e.patient.id END " +
            " and e.clinic.id =  CASE WHEN :clinicId > 0L THEN :clinicId ELSE e.clinic.id END  " +
            " and e.id =  CASE WHEN :id > 0L THEN :id ELSE e.id END " +
            " and e.status =  CASE WHEN :status >= 0 THEN :status ELSE e.status END "

    )
    Page<Refer> searchRefer(@Param("referDateShamsiFrom") String referDateShamsiFrom,
                            @Param("referDateShamsiTo") String referDateShamsiTo,
                            @Param("receptionDateShamsiFrom") String receptionDateShamsiFrom,
                            @Param("receptionDateShamsiTo") String receptionDateShamsiTo,
                            @Param("finishDateShamsiFrom") String finishDateShamsiFrom,
                            @Param("finishDateShamsiTo") String finishDateShamsiTo,
                            @Param("doctorId") Long doctorId,
                            @Param("patientId") Long patientId,
                            @Param("clinicId") Long clinicId,
                            @Param("id") Long id,
                            @Param("status") Integer status,
                            Pageable pageable);

    List<Refer> getAllByClinicEqualsAndStatusEqualsAndSettlementEquals(Clinic clinic, ReferStatus status, Settlement settlement);

    @Query("SELECT e FROM Refer e where e.settlement.id = :settlementId " +
            " and e.patient.id =  CASE WHEN :patientId > 0L THEN :patientId ELSE e.patient.id END " +
            " and e.doctor.id =  CASE WHEN :doctorId > 0L THEN :doctorId ELSE e.doctor.id END  " +
            " and e.id =  CASE WHEN :id > 0L THEN :id ELSE e.id END ")
    Page<Refer> getAllBySettlementId(@Param("settlementId") long settlementId,
                                     @Param("doctorId") Long doctorId,
                                     @Param("patientId") Long patientId,
                                     @Param("id") Long id,
                                     Pageable pageable);

    @Query("SELECT new com.bzamani.framework.dto.ReferPieChartDto(e.status as status , count(e.id) as percent) FROM Refer e where " +
            "exists ( select 1 from Doctor d join d.personel p join UserOrganizationAuthorize oa on oa.organizationId = p.organization.id " +
            "           join User u on u.id = oa.userId " +
            "          where d.id = e.doctor.id and u.username = :username ) " +
            " or " +
            " exists ( select 1 from Clinic c " +
            "    join UserOrganizationAuthorize oa on oa.organizationId = c.organization.id " +
            "           join User u on u.id = oa.userId " +
            "          where c.id = e.clinic.id and u.username = :username )" +
            " group by e.status order by count(e.id) desc "
    )
    List<ReferPieChartDto> getAllRefersCountGroupByStatus(@Param("username") String username);

    @Query("SELECT new com.bzamani.framework.dto.ReferChartDto(e.doctor.personel.firstname || ' ' " +
            " || e.doctor.personel.lastname as key , count(e.id) as value) FROM Refer e where e.status in( 1,2,3,4 )and" +
            "(exists ( select 1 from Doctor d join d.personel p join UserOrganizationAuthorize oa on oa.organizationId = p.organization.id " +
            "           join User u on u.id = oa.userId " +
            "          where d.id = e.doctor.id and u.username = :username ) " +
            " or " +
            " exists ( select 1 from Clinic c " +
            "    join UserOrganizationAuthorize oa on oa.organizationId = c.organization.id " +
            "           join User u on u.id = oa.userId " +
            "          where c.id = e.clinic.id and u.username = :username ))" +
            " group by e.doctor.personel.firstname , e.doctor.personel.lastname order by count(e.id) desc "
    )
    List<ReferChartDto> getAllRefersGroupByDoctors(@Param("username") String username);

    @Query("SELECT new com.bzamani.framework.dto.ReferChartDto(e.clinic.organization.title as key , count(e.id) as value) FROM Refer e where e.status in(3,4) and" +
            "(exists ( select 1 from Doctor d join d.personel p join UserOrganizationAuthorize oa on oa.organizationId = p.organization.id " +
            "           join User u on u.id = oa.userId " +
            "          where d.id = e.doctor.id and u.username = :username ) " +
            " or " +
            " exists ( select 1 from Clinic c " +
            "    join UserOrganizationAuthorize oa on oa.organizationId = c.organization.id " +
            "           join User u on u.id = oa.userId " +
            "          where c.id = e.clinic.id and u.username = :username ))" +
            " group by e.clinic.organization.title order by count(e.id) desc "
    )
    List<ReferChartDto> getAllRefersGroupByClinics(@Param("username") String username);


}
