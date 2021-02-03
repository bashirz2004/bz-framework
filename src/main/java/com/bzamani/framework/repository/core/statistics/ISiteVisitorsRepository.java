package com.bzamani.framework.repository.core.statistics;

import com.bzamani.framework.model.core.statistics.SiteVisitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISiteVisitorsRepository extends JpaRepository<SiteVisitors, Long> {
    @Query(" select count(e.id) from SiteVisitors e where 1 = 1 " +
            " and coalesce(e.dateShamsi , '') >=  case when :fromDateShamsi = '' then coalesce(e.dateShamsi , '') else :fromDateShamsi end " +
            " and coalesce(e.dateShamsi , '') <=  case when :toDateShamsi = '' then coalesce(e.dateShamsi , '') else :toDateShamsi end ")
    long getCountOfVisitors(@Param("fromDateShamsi") String fromDateShamsi, @Param("toDateShamsi") String toDateShamsi);
}
