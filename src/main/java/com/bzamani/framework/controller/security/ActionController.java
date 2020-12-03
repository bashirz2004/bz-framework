package com.bzamani.framework.controller.security;

import com.bzamani.framework.model.security.Action;
import com.bzamani.framework.service.security.action.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/core/action", produces = "application/json;charset=UTF-8")
public class ActionController {

    @Autowired
    IActionService iActionService;

    @PostMapping("/save")
    public Action create(@RequestBody Action action) {
        return iActionService.save(action);
    }

    @GetMapping("/load/{id}")
    public Action load(@PathVariable("id") long id) {
        return iActionService.loadByEntityId(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iActionService.deleteByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<Action> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iActionService.getAll(sort);
    }

    @GetMapping("/getAllGrid")
    public Map<String, Object> getAllGrid(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {
        return iActionService.getAllGrid(page, size, sort);
    }

}
