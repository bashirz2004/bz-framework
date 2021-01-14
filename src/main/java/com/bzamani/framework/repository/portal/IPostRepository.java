package com.bzamani.framework.repository.portal;

import com.bzamani.framework.model.core.personel.Personel;
import com.bzamani.framework.model.portal.Post;
import javafx.geometry.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT e FROM Post e where 1 = 1  " +
            " and (  e.title like '%' || coalesce(cast( :searchBox as text), e.title ) || '%'" +
            "      or e.body like '%' || coalesce(cast( :searchBox as text), e.body ) || '%'" +
            "     or e.tags like '%' || coalesce(cast( :searchBox as text), e.tags ) || '%'" +
            "     )" +
            " and e.category.id =  CASE WHEN :categoryId > 0L THEN :categoryId ELSE e.category.id END " +
            " and e.confirmed = CASE WHEN :confirmed is null THEN e.confirmed ELSE :confirmed END  ")
    Page<Post> searchPost(@Param("searchBox") String searchBox, @Param("categoryId") Long categoryId, @Param("confirmed") Boolean confirmed, Pageable pageable);

}
