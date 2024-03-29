package com.bzamani.framework.repository.core.action;

import com.bzamani.framework.dto.HierarchicalObjectDto;
import com.bzamani.framework.dto.MenuTreeNodeDto;
import com.bzamani.framework.model.core.action.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IActionRepository extends JpaRepository<Action, Long> {
    /*@Query("select distinct a FROM User u join u.groups g join g.actions a where u.id = :userId and a.menu = true order by a.sortOrder ")
    List<Action> loadMenuForCurrentUser(@Param("userId") long userId);*/

    @Query("	select  distinct new com.bzamani.framework.dto.MenuTreeNodeDto(                     " +
            "		a.id as id,                                                                     " +
            "		a.title as title,															    " +
            "		a.parent.id as parentId,                                                        " +
            "       a.sortOrder as sortOrder ,                                                      " +
            "       a.src as src,                                                                   " +
            "       a.iconClass as iconClass ) 												 	    " +
            "	from User u join u.groups ug join ug.actions a                                      " +
            "   where u.id = :userId                                                                " +
            "     and a.parent.id = :parentId                                                       " +
            "     and a.menu = true                                                                 " +
            "   order by a.sortOrder                                                                ")
    List<MenuTreeNodeDto> getAuthorizeActionsForUserId(@Param("userId") long userId, @Param("parentId") Long parentId);

    List<Action> findActionsByParentOrderBySortOrder(Action parent);
}
