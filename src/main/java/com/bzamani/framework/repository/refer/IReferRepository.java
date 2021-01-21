package com.bzamani.framework.repository.refer;

import com.bzamani.framework.model.refer.Refer;
import com.bzamani.framework.model.refer.ReferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IReferRepository extends JpaRepository<Refer, Long> {
    @Query("SELECT e FROM Refer e ")
    Page<Refer> searchRefer(@Param("referDateShamsiFrom") String referDateShamsiFrom,
                            @Param("referDateShamsiTo") String referDateShamsiTo,
                            @Param("doctorId") Long doctorId,
                            @Param("patientId") Long patientId,
                            @Param("clinicId") Long clinicId,
                            @Param("sicknessId") Long sicknessId,
                            @Param("treatmentId") Long treatmentId,
                            @Param("receptionDateShamsiFrom") String receptionDateShamsiFrom,
                            @Param("receptionDateShamsiTo") String receptionDateShamsiTo,
                            @Param("finishDateShamsiFrom") String finishDateShamsiFrom,
                            @Param("finishDateShamsiTo") String finishDateShamsiTo,
                            @Param("settlementDateShamsiFrom") String settlementDateShamsiFrom,
                            @Param("settlementDateShamsiTo") String settlementDateShamsiTo,
                            @Param("status") ReferStatus status,
                            Pageable pageable);


}
