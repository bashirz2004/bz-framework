package com.bzamani.framework.repository.core.organization;

import com.bzamani.framework.model.core.organization.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrganizationRepository extends JpaRepository<Organization, Long>{

    @Query( "SELECT e FROM Organization e where 1=1 " +
            " and e.title = COALESCE(cast(:title AS text), e.title) " +
            " and e.description =  COALESCE(cast(:description AS text), e.description) " +
            " and e.active = CASE WHEN :active is null THEN e.active ELSE :active END ")
    Page<Organization> getAllGridByMyQuery(@Param("title") String title, @Param("description") String description, @Param("active") Boolean active, Pageable pageable);

    /*@Query("SELECT u FROM Organization u ")
    Page<Organization> staticQuery(@Param("title") String title, @Param("description") String description, @Param("active") Boolean active, Pageable pageable);*/

   /* @Modifying
    @Query("update Organization set active = :active where id = :id")
    long changeStatus(@Param("id") long id, @Param("active") boolean active);*/
}
