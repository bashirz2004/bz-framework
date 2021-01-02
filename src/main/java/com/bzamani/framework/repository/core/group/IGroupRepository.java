package com.bzamani.framework.repository.core.group;

import com.bzamani.framework.model.core.group.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT e FROM Group e where e.title like COALESCE(cast('%'||:title||'%' AS text), '%'||e.title)||'%'  ")
    Page<Group> searchGroup(@Param("title") String title, Pageable pageable);

    @Query("  select count(u.id) from User u join u.groups g where u.id = :userId and g.id = :groupId ")
    int userHaveAccessToGroup(@Param("userId") long userId, @Param("groupId") long groupId);

    @Query("SELECT g FROM User u join u.groups g where u.id =:userId and g.id <> 1 ")
    List<Group> getAuthorizeGroupsForUserId(@Param("userId") long userId);
}
