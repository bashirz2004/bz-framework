package com.bzamani.framework.service.core.personel;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface IPersonelService extends IGenericService<Personel, Long> {
    @Transactional
    Personel checkAndSave(Personel personel) ;

    @Transactional
    Personel saveBrief(Personel personel);

    @Transactional
    boolean checkAndDelete(Long id) ;

    Map<String, Object> searchPersonel(String firstname,
                                       String lastname,
                                       String mobile,
                                       Long organizationId, int page, int size, String[] sort);

    Map<String, Object> searchPersonelAuthorize(String firstname,
                                                String lastname,
                                                String mobile,
                                                Long organizationId, int page, int size, String[] sort);

    Personel findByEmailEquals(String email);

    Personel findByMobileEquals(String mobile);

}
