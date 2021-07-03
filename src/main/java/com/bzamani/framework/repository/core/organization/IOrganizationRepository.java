package com.bzamani.framework.repository.core.organization;

import com.bzamani.framework.dto.HierarchicalObjectDto;
import com.bzamani.framework.model.core.organization.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IOrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT e FROM Organization e where 1=1 " +
            " and e.title like COALESCE(cast('%'||:title||'%' AS text), '%'||e.title)||'%'  " +
            " and e.active = CASE WHEN :active is null THEN e.active ELSE :active END ")
    Page<Organization> searchOrganization(@Param("title") String title, @Param("active") Boolean active, Pageable pageable);

    @Query("SELECT e FROM Organization e where e.parent is null ")
    List<Organization> getRoot();

    @Query("SELECT e FROM Organization e where e.parent.id = :parentId order by e.title asc ")
    List<Organization> getAllByParentId(@Param("parentId") Long parentId);

    @Query(" select cp3.id from Organization cp1,  Organization cp2, Organization cp3 "
            + "	where 	cp1.id = :organizationId and cp1.hierarchyCode like cp3.hierarchyCode||'%' and "
            + "			cp3.hierarchyCode like cp2.hierarchyCode||'%'  and	cp2.parent.id is null  "
            + " group by cp3.id, cp3.hierarchyCode order by cp3.hierarchyCode asc ")
    List<Long> getAllParentIds(@Param("organizationId") Long organizationId);

    @Query( "	select  new com.bzamani.framework.dto.HierarchicalObjectDto(                        " +
            "		p.id as id,  																	" +
            "		p.title as title,																" +
            "		p.parent.id as parentId,                                                        " +
            "   	cast((select count(a.id) from Organization a where a.parent.id = p.id) as integer) as childCount ) " +
            "	from Organization p                                                                 " +
            "	where p.active = true and  p.hierarchyCode in (                                                          " +
            "              select substring(org.hierarchyCode,1,length(p.hierarchyCode))            " +
            "   	          from UserOrganizationAuthorize uoa,Organization org                  " +
            "   	        where uoa.userId = :userId and uoa.organizationId = org.id   )                             " +
            "     and p.parent.id = :parentId order by p.title                                      "
    )
    List<HierarchicalObjectDto> getAuthorizeOrganizationsForUserId(@Param("userId") long userId, @Param("parentId") Long parentId);

    @Query("  select count(uoa.userId) from UserOrganizationAuthorize uoa where uoa.userId = :userId and uoa.organizationId = :organizationId ")
    int userHaveAccessToOrganization(@Param("userId") long userId, @Param("organizationId") long organizationId);


}
