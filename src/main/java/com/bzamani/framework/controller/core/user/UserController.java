package com.bzamani.framework.controller.core.user;

import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@PreAuthorize("hasRole('2')")
@RestController
@RequestMapping(value = "/rest/core/user", produces = "application/json;charset=UTF-8")
public class UserController extends BaseController {

    @Autowired
    IUserService iUserService;

    @Value("#{bzFrmMessages['myTest']}") //read from my properties file
    String testPropFile;

    @Value("${server.port}") //read from application.yml file
    String testYAMLFile;

    @PreAuthorize("hasRole('3')")
    @PostMapping("/save")
    public User create(@RequestBody User user) {
        System.out.println("-------------------- " + testPropFile);
        System.out.println("-------------------- " + testYAMLFile);
        return iUserService.save(user);
    }

    @PreAuthorize("hasRole('3')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iUserService.deleteByEntityId(id);
    }

    @GetMapping("/load/{id}")
    public User load(@PathVariable("id") long id) {
        return iUserService.loadByEntityId(id);
    }

    @GetMapping("/getAll")
    public List<User> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
        return iUserService.getAll(sort);
    }

    @GetMapping("/getAllGrid")
    public Map<String, Object> getAllGrid(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size,
                                          @RequestParam(defaultValue = "id,desc") String[] sort) {
        return iUserService.getAllGrid(page, size, sort);
    }

}
