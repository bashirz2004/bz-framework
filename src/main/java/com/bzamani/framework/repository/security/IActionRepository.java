package com.bzamani.framework.repository.security;

import com.bzamani.framework.model.security.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IActionRepository extends JpaRepository<Action, Long> {

}
