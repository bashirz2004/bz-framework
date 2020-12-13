package com.bzamani.framework.repository.core.action;

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
    @Query("select e FROM User u inner join u.actions e where u.id = :userId and e.menu = true order by e.sortOrder ")
    List<Action> loadMenuForCurrentUser(@Param("userId") long userId);
}
