package com.bzamani.framework.repository.core.personel;

import com.bzamani.framework.model.core.personel.Personel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface PersonelCustomRepository {
    Page<Personel> searchPersonel(@Param("firstname") String firstname,
                                  @Param("lastname") String lastname,
                                  @Param("mobile") String mobile,
                                  @Param("organizationId") Long organizationId,
                                  Pageable pageable);
}
