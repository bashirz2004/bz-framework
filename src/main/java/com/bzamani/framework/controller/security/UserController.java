package com.bzamani.framework.controller.security;

import com.bzamani.framework.controller.BaseController;
import com.bzamani.framework.model.security.User;
import com.bzamani.framework.service.security.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/core/user")
public class UserController extends BaseController {

  @Autowired
  IUserService iUserService;

  @PostMapping("/create")
  public User create(@RequestBody User user) {
    return iUserService.create(user);
  }

  @GetMapping("/load/{id}")
  public User load(@PathVariable("id") long id) {
    return iUserService.load(id);
  }

  @PutMapping("/update")
  public User update(@RequestBody User user) {
    return iUserService.update(user);
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") long id) {
    iUserService.delete(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
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
