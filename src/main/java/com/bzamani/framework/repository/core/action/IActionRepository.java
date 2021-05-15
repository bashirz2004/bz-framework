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
    @Query("select distinct a FROM User u join u.groups g join g.actions a where u.id = :userId and a.menu = true order by a.sortOrder ")
    List<Action> loadMenuForCurrentUser(@Param("userId") long userId);

    @Query("	select  distinct new com.bzamani.framework.dto.MenuTreeNodeDto(              " +
            "		p.id as id,                                                                    " +
            "		p.title as title,															   " +
            "		p.parent.id as parentId,                                                       " +
            "       p.sortOrder as sortOrder , " +
            "       p.src as src," +
            "       p.iconClass as iconClass ) 													   " +
            "	from Group g join g.actions p                                                                     " +
            "	where p.hierarchyCode in (                                                          " +
            "              select substring(act.hierarchyCode,1,length(p.hierarchyCode))            " +
            "   	          from User u join u.groups ug join ug.actions ua,Action act            " +
            "   	        where u.id = :userId and ua.id = act.id   )                             " +
            "     and p.parent.id = :parentId " +
            "     and p.menu = true                                                 " +
            "   order by p.sortOrder                                                                ")
    List<MenuTreeNodeDto> getAuthorizeActionsForUserId(@Param("userId") long userId, @Param("parentId") Long parentId);

    List<Action> findActionsByParentOrderBySortOrder(Action parent);
}
