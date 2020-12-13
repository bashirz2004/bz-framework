package com.bzamani.framework.controller.core.baseinfo;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.baseinfo.BaseInfoHeader;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/core/baseinfoheader", produces = "application/json;charset=UTF-8")
public class BaseInfoHeaderController extends BaseController {

    @Autowired
    IBaseInfoHeaderService iBaseInfoHeaderService;

    @PostMapping("/save")
    public BaseInfoHeader save(@RequestBody BaseInfoHeader baseInfoHeader) {
        return iBaseInfoHeaderService.save(baseInfoHeader);
    }

    @GetMapping("/load/{id}")
    public BaseInfoHeader load(@PathVariable("id") long id) {
        return iBaseInfoHeaderService.loadByEntityId(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iBaseInfoHeaderService.deleteByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<BaseInfoHeader> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iBaseInfoHeaderService.getAll(sort);
    }

    @GetMapping("/getAllGrid")
    public Map<String, Object> getAllGrid(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {
        return iBaseInfoHeaderService.getAllGrid(page, size, sort);
    }

}
