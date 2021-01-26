package com.bzamani.framework.controller.refer;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.refer.ReferLog;
import com.bzamani.framework.service.refer.IReferLogService;
import com.bzamani.framework.service.refer.IReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/refer/referLog", produces = "application/json;charset=UTF-8")
public class ReferLogController extends BaseController {
    @Autowired
    IReferLogService iReferLogService;

    @Autowired
    IReferService iReferService;

    @GetMapping("/findAllByReferId/{referId}")
    public List<ReferLog> findAllByReferId(@PathVariable long referId) {
        return iReferLogService.findAllByReferEqualsOrderByCreateDateDesc(iReferService.loadByEntityId(referId));
    }
}
