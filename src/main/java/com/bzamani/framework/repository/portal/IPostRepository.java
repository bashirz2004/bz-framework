package com.bzamani.framework.repository.portal;

import com.bzamani.framework.dto.PostCategoryDto;
import com.bzamani.framework.model.portal.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    @Query("select new com.bzamani.framework.dto.PostCategoryDto(e.id as id," +
            "                                                    e.title as title," +
            "                                                    (select count(*) from Post a where a.category.id=e.id " +
            "                                                      and (  a.title like '%' || coalesce(cast( :searchBox as text), a.title ) || '%'" +
            "                                                          or a.body like '%' || coalesce(cast( :searchBox as text), a.body ) || '%'" +
            "                                                          or a.tags like '%' || coalesce(cast( :searchBox as text), a.tags ) || '%'" +
            "                                                          )" +
            "                                                      and a.confirmed = CASE WHEN :confirmed is null THEN a.confirmed ELSE :confirmed END  " +
            "                                                    ) as numberOfPosts) " +
            "   from BaseInfo e where e.header.id = 4L and e.parent is null" +
            " and exists(select 1 from Post p where p.category.id = e.id " +
            "             and (  p.title like '%' || coalesce(cast( :searchBox as text), p.title ) || '%'" +
            "                 or p.body like '%' || coalesce(cast( :searchBox as text), p.body ) || '%'" +
            "                 or p.tags like '%' || coalesce(cast( :searchBox as text), p.tags ) || '%'" +
            "                 )" +
            " and p.confirmed = CASE WHEN :confirmed is null THEN p.confirmed ELSE :confirmed END  " +
            "           ) " +
            "order by e.code,e.title ")
    List<PostCategoryDto> getAllUsedPostCategories(@Param("searchBox") String searchBox, @Param("confirmed") Boolean confirmed);

    @Modifying
    @Query("update Post e set e.confirmed = true , e.lastUpdateDate = :now where e.id = :id ")
    Integer confirmPost(@Param("id") long id, @Param("now") Date now);


}
