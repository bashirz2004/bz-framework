package com.bzamani.framework.repository.refer;

import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            " and e.id =  CASE WHEN :id > 0L THEN :id ELSE e.id END "+
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


}
