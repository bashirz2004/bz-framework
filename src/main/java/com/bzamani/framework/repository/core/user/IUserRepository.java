package com.bzamani.framework.repository.core.user;

import com.bzamani.framework.model.core.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByUsernameEquals(String username);

    @Query("SELECT e FROM User e where 1 = 1  " +
            " and e.personel.firstname like COALESCE(cast('%'||:firstname||'%' AS text), '%'||e.personel.firstname)||'%'  " +
            " and e.personel.lastname like COALESCE(cast('%'||:lastname||'%' AS text), '%'||e.personel.lastname)||'%' " +
            " and case when e.personel.nationalCode is null then 'foo' else e.personel.nationalCode end like '%' || coalesce(cast( :nationalCode as text), case when e.personel.nationalCode is null then 'foo' else e.personel.nationalCode end) || '%'" +
            " and e.personel.organization.id =  CASE WHEN :organizationId > 0L THEN :organizationId ELSE e.personel.organization.id END " +
            " and e.username like COALESCE(cast('%'||:username||'%' AS text), '%'||e.username)||'%'  " +
            " and e.accountNonExpired = CASE WHEN :accountNonExpired is null THEN e.accountNonExpired ELSE :accountNonExpired END " +
            " and e.accountNonLocked = CASE WHEN :accountNonLocked is null THEN e.accountNonLocked ELSE :accountNonLocked END " +
            " and e.credentialsNonExpired = CASE WHEN :credentialsNonExpired is null THEN e.credentialsNonExpired ELSE :credentialsNonExpired END " +
            " and e.enabled = CASE WHEN :enabled is null THEN e.enabled ELSE :enabled END ")
    Page<User> searchUser(@Param("firstname") String firstname,
                          @Param("lastname") String lastname,
                          @Param("nationalCode") String nationalCode,
                          @Param("organizationId") Long organizationId,
                          @Param("username") String username,
                          @Param("accountNonExpired") Boolean accountNonExpired,
                          @Param("accountNonLocked") Boolean accountNonLocked,
                          @Param("credentialsNonExpired") Boolean credentialsNonExpired,
                          @Param("enabled") Boolean enabled,
                          Pageable pageable);

    @Modifying
    @Query("update User e set e.password = :newPassword , e.lastUpdateDate = :now where exists (select 1 from User u join Personel p on p.id = u.personel.id where u.id = e.id and p.email = :email )")
    Integer changePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword, @Param("now") Date now);

    @Query("from User e where exists (select 1 from User u join Personel p on p.id = u.personel.id where u.id = e.id and p.email = :email )")
    User findByEmail(@Param("email") String email);

    List<User> findAllByIpOrderByLastUpdateDateDesc(String ip);
}
