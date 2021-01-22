package com.bzamani.framework.repository.core.personel;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonelRepository extends JpaRepository<Personel, Long> {
    @Query("SELECT e FROM Personel e left join e.state s left join e.city c left join e.region r where 1 = 1  " +
            " and e.firstname like COALESCE(cast('%'||:firstname||'%' AS text), '%'||e.firstname)||'%'  " +
            " and e.lastname like COALESCE(cast('%'||:lastname||'%' AS text), '%'||e.lastname)||'%' " +
            " and case when e.mobile is null then 'foo' else e.mobile end like '%' || coalesce(cast( :mobile as text), case when e.mobile is null then 'foo' else e.mobile end) || '%'" +
            " and e.organization.id =  CASE WHEN :organizationId > 0L THEN :organizationId ELSE e.organization.id END ")
    Page<Personel> searchPersonel(@Param("firstname") String firstname,
                                  @Param("lastname") String lastname,
                                  @Param("mobile") String mobile,
                                  @Param("organizationId") Long organizationId,
                                  Pageable pageable);

    @Query("SELECT e FROM Personel e left join e.state s left join e.city c left join e.region r where 1 = 1  " +
            " and e.firstname like COALESCE(cast('%'||:firstname||'%' AS text), '%'||e.firstname)||'%'  " +
            " and e.lastname like COALESCE(cast('%'||:lastname||'%' AS text), '%'||e.lastname)||'%' " +
            " and case when e.mobile is null then 'foo' else e.mobile end like '%' || coalesce(cast( :mobile as text), case when e.mobile is null then 'foo' else e.mobile end) || '%'" +
            " and e.organization.id =  CASE WHEN :organizationId > 0L THEN :organizationId ELSE e.organization.id END " +
            " and exists (from UserOrganizationAuthorize uoa where uoa.userId = :userId and uoa.organizationId = e.organization.id)")
    Page<Personel> searchPersonelAuthorize(@Param("firstname") String firstname,
                                           @Param("lastname") String lastname,
                                           @Param("mobile") String mobile,
                                           @Param("organizationId") Long organizationId,
                                           @Param("userId") long userId,
                                           Pageable pageable);

    Personel findByEmailEquals(String email);

    Personel findByMobileEquals(String mobile);


}
