package com.bzamani.framework.service.impl.core.statistics;

import com.bzamani.framework.model.core.statistics.SiteVisitors;
import com.bzamani.framework.repository.core.statistics.ISiteVisitorsRepository;
import com.bzamani.framework.service.core.statistics.ISiteVisitorsService;
import com.bzamani.framework.service.impl.core.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SiteVisitorsService extends GenericService<SiteVisitors, Long> implements ISiteVisitorsService {
    @Autowired
    ISiteVisitorsRepository iSiteVisitorsRepository;

    @Override
    protected JpaRepository<SiteVisitors, Long> getGenericRepo() {
        return iSiteVisitorsRepository;
    }

    @Override
    public long getCountOfVisitors(String fromDateShamsi, String toDateShamsi){
        return iSiteVisitorsRepository.getCountOfVisitors(fromDateShamsi,toDateShamsi);
    }

}
