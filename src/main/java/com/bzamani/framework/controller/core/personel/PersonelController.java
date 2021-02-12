package com.bzamani.framework.controller.core.personel;


import com.bzamani.framework.common.config.dozermapper.ModelMapper;
import com.bzamani.framework.controller.core.BaseController;
import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.service.core.personel.IPersonelService;
import com.bzamani.framework.viewmodel.core.personel.PersonelViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/core/personel", produces = "application/json;charset=UTF-8")
public class PersonelController extends BaseController {
    @Autowired
    IPersonelService iPersonelService;

    @PreAuthorize("hasRole('9')")
    @PostMapping("/save")
    public Personel save(@RequestBody PersonelViewModel personelViewModel) {
        return iPersonelService.checkAndSave(ModelMapper.map(personelViewModel, Personel.class, true));
    }

    @PreAuthorize("hasRole('9')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") long id) {
        return iPersonelService.checkAndDelete(id);
    }

    @GetMapping("/load/{id}")
    public PersonelViewModel load(@PathVariable("id") long id) {
        return ModelMapper.map(iPersonelService.loadByEntityId(id), PersonelViewModel.class);
    }

    @GetMapping("/searchPersonels")
    public Map<String, Object> searchPersonels(@RequestParam(required = false) String firstname,
                                               @RequestParam(required = false) String lastname,
                                               @RequestParam(required = false) String mobile,
                                               @RequestParam(required = false) Long organizationId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "8") int size,
                                               @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iPersonelService.searchPersonel(firstname,
                lastname,
                mobile,
                organizationId, page, size, sort);
    }

    @GetMapping("/searchPersonelAuthorize")
    public Map<String, Object> searchPersonelAuthorize(@RequestParam(required = false) String firstname,
                                                       @RequestParam(required = false) String lastname,
                                                       @RequestParam(required = false) String mobile,
                                                       @RequestParam(required = false) Long organizationId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "8") int size,
                                                       @RequestParam(defaultValue = "id,desc") String[] sort) {

        return iPersonelService.searchPersonelAuthorize(firstname,
                lastname,
                mobile,
                organizationId, page, size, sort);
    }
}
