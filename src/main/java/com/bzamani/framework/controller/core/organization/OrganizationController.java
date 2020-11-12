package com.bzamani.framework.controller.core.organization;

import com.bzamani.framework.controller.BaseController;
import com.bzamani.framework.model.core.organization.Organization;
import com.bzamani.framework.service.core.organization.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/core/organization")
public class OrganizationController extends BaseController {

  @Autowired
  IOrganizationService iOrganizationService;

  private Sort.Direction getSortDirection(String direction) {
    if (direction.equals("asc"))
      return Sort.Direction.ASC;
    else if (direction.equals("desc"))
      return Sort.Direction.DESC;
    return Sort.Direction.ASC;
  }

  @PostMapping("/save")
  public Organization save(@RequestBody Organization organization) {
    return iOrganizationService.save(organization);
  }

  @GetMapping("/load/{id}")
  public Organization load(@PathVariable("id") long id) {
    return iOrganizationService.loadByEntityId(id);
  }

  @DeleteMapping("/delete/{id}")
  public boolean delete(@PathVariable("id") long id) {
    return iOrganizationService.deleteByEntityId(id);
  }

  @GetMapping("/getAll")
  public List<Organization> getAll(@RequestParam(defaultValue = "id,desc") String[] sort) {
    return iOrganizationService.getAll(sort);
  }

  @GetMapping("/getAllGrid")
  public Map<String, Object> getAllGrid(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "id,desc") String[] sort) {
    return iOrganizationService.getAllGrid(page, size, sort);
  }

  @GetMapping("/getAllGridByMyQuery")
  public Map<String, Object> getAllGridByMyQuery(
    @RequestParam(required = false) String title,
    @RequestParam(required = false) String description,
    @RequestParam(required = false) Boolean active,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "3") int size,
    @RequestParam(defaultValue = "id,desc") String[] sort) {

    return iOrganizationService.getAllGridByMyQuery(title, description, active, page, size, sort);
  }
}
