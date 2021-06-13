package com.bzamani.framework.repository.core.personel;

import com.bzamani.framework.model.core.personel.Personel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public class PersonelCustomRepositoryImpl implements PersonelCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Personel> searchPersonel(@Param("firstname") String firstname,
                                         @Param("lastname") String lastname,
                                         @Param("mobile") String mobile,
                                         @Param("organizationId") Long organizationId,
                                         Pageable pageable) {
        Map<String, Object> parameters = new HashMap<>();
        String hql = "SELECT e FROM Personel e WHERE 1 = 1 ";

        if (firstname != null && firstname.trim().length() > 0) {
            hql += " and e.firstname like :firstname ";
            parameters.put("firstname", '%' + firstname + '%');
        }
        if (lastname != null && lastname.trim().length() > 0) {
            hql += " and e.lastname like :lastname ";
            parameters.put("lastname", '%' + lastname + '%');
        }
        if (mobile != null && mobile.trim().length() > 0) {
            hql += " and e.mobile like :mobile ";
            parameters.put("mobile", '%' + mobile + '%');
        }
        if (organizationId != null && organizationId > 0L) {
            hql += " and e.organization.id = :organizationId ";
            parameters.put("organizationId", organizationId);
        }
        if (!pageable.getSort().isEmpty()) {
            hql += " order by " + pageable.getSort().get().findFirst().get().getProperty() + " " + pageable.getSort().get().findFirst().get().getDirection();
        }

        Query query = entityManager.createQuery(hql);
        parameters.entrySet().iterator().forEachRemaining(a -> {
            query.setParameter(a.getKey(), a.getValue());
        });

        int totalRecords = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<Personel>(query.getResultList(), pageable, totalRecords);
    }
}
