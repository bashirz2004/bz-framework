package com.bzamani.framework.controller.baseinfo;

import com.bzamani.framework.controller.BaseController;
import com.bzamani.framework.model.baseinfo.BaseInfo;
import com.bzamani.framework.service.baseinfo.IBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/core/baseinfo", produces = "application/json;charset=UTF-8")
public class BaseInfoController extends BaseController {

    @Autowired
    IBaseInfoService iBaseInfoService;

    @PostMapping("/save")
    public BaseInfo save(@RequestBody BaseInfo baseInfo) {
        return iBaseInfoService.save(baseInfo);
    }

    @GetMapping("/load/{id}")
    public BaseInfo load(@PathVariable("id") long id) {
        return iBaseInfoService.loadByEntityId(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iBaseInfoService.deleteByEntityId(id);
    }

    @GetMapping("/getAllByHeaderId/{headerId}")
    public List<BaseInfo> getAllByHeaderId(@PathVariable long headerId) {
        return iBaseInfoService.getAllByHeaderId(headerId);
    }
}
