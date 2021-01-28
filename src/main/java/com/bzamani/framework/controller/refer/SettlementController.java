package com.bzamani.framework.controller.refer;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.refer.Settlement;
import com.bzamani.framework.service.refer.ISettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/refer/settlement", produces = "application/json;charset=UTF-8")
public class SettlementController extends BaseController {
    @Autowired
    ISettlementService iSettlementService;

    @PreAuthorize("hasRole('1009')")
    @PostMapping("/save")
    public Settlement save(@RequestBody Settlement settlement) {
        return iSettlementService.savSettlement(settlement);
    }

    @PreAuthorize("hasRole('1009')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iSettlementService.deleteSettlement(id);
    }

    @GetMapping("/load/{id}")
    public Settlement load(@PathVariable("id") long id) {
        return iSettlementService.loadByEntityId(id);
    }

    @GetMapping("/searchSettlement")
    public Map<String, Object> searchSettlement(
            @RequestParam(required = false) String settlementDateShamsiFrom,
            @RequestParam(required = false) String settlementDateShamsiTo,
            @RequestParam(required = false) Long clinicId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean confirmed,
            @RequestParam(required = false) Long referId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iSettlementService.searchSettlement(settlementDateShamsiFrom, settlementDateShamsiTo,
                clinicId, description, confirmed, referId, page, size, sort);
    }

    @PostMapping("/confirm/{id}")
    public Long confirm(@PathVariable long id)  {
        return iSettlementService.confirm(id);
    }
}
