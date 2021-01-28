package com.bzamani.framework.repository.refer;

import com.bzamani.framework.model.refer.Settlement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ISettlementRepository extends JpaRepository<Settlement, Long> {
    @Query("select distinct e from Settlement e left join e.refers r on r.settlement.id = e.id  where 1 = 1" +
            " and coalesce(e.settlementDateShamsi , '') >=  case when :settlementDateShamsiFrom = '' then coalesce(e.settlementDateShamsi , '') else :settlementDateShamsiFrom end " +
            " and coalesce(e.settlementDateShamsi , '') <=  case when :settlementDateShamsiTo = '' then coalesce(e.settlementDateShamsi , '') else :settlementDateShamsiTo end " +
            " and e.clinic.id =  CASE WHEN :clinicId > 0L THEN :clinicId ELSE e.clinic.id END  " +
            " and case when e.description is null then 'foo' else e.description end like '%' || coalesce(cast( :description as text), case when e.description is null then 'foo' else e.description end) || '%'" +
            " and e.confirmed =  CASE WHEN :confirmed = true THEN true WHEN :confirmed = false THEN false  ELSE e.confirmed END " +
            " and coalesce(r.id , 0) =  CASE WHEN :referId > 0L THEN :referId ELSE coalesce(r.id , 0)  END  "
    )
    Page<Settlement> searchSettlement(@Param("settlementDateShamsiFrom") String settlementDateShamsiFrom,
                                      @Param("settlementDateShamsiTo") String settlementDateShamsiTo,
                                      @Param("clinicId") Long clinicId,
                                      @Param("description") String description,
                                      @Param("confirmed") Boolean confirmed,
                                      @Param("referId") Long referId,
                                      Pageable pageable);


}
