package com.bzamani.framework.controller.core.baseinfo;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.baseinfo.BaseInfo;
import com.bzamani.framework.service.core.baseinfo.IBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/getAllByParentId/{parentId}")
    public List<BaseInfo> getAllByParentId(@PathVariable long parentId) {
        return iBaseInfoService.getAllByParentId(parentId);
    }

    @GetMapping(value = "/getAllHeadersAsXml", produces = "text/plain;charset=UTF-8")
    public String getAllHeadersAsXml() {
        return iBaseInfoService.getAllHeadersAsXml();
    }

    @GetMapping(value = "/getChildsAsXml/{id}", produces = "text/plain;charset=UTF-8")
    public String getChildsAsXml(@PathVariable String id) {
        return iBaseInfoService.getChildsAsXml(id);
    }

    @GetMapping("/searchBaseInfo")
    public Map<String, Object> searchBaseInfo(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title,asc") String[] sort) {
        return iBaseInfoService.searchBaseInfo(title, page, size, sort);
    }

    @RequestMapping(value = "/reMakeTreeAfterAutoComplete/{id}", produces = "text/plain;charset=UTF-8")
    public String reMakeTreeAfterAutoComplete(@PathVariable Long id) {
        return iBaseInfoService.reMakeTreeAfterAutoComplete(id);
    }

}
