package com.bzamani.framework.service.core.statistics;

import com.bzamani.framework.model.core.statistics.SiteVisitors;
import com.bzamani.framework.service.core.IGenericService;

public interface ISiteVisitorsService extends IGenericService<SiteVisitors, Long> {

    long getCountOfVisitors(String fromDateShamsi, String toDateShamsi);
}
