package com.bzamani.framework.repository.portal;

import com.bzamani.framework.model.portal.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends JpaRepository<Like, Long> {

}
