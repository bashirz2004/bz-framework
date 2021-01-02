package com.bzamani.framework.service.core.user;

import com.bzamani.framework.dto.SelfUserRegistrationDto;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IUserService extends IGenericService<User, Long> {
    User findUserByUsernameEquals(String username);

    @Transactional
    User saveUserWithSets(User user);

    User selfRegister(SelfUserRegistrationDto userDto) throws Exception;

    @Transactional
    void sendPasswordToUserEmail(String email) throws Exception;

    @Transactional
    boolean changePasswordByAdmin(Long userId, String newPassword);

    Map<String, Object> searchUser(String firstname,
                                   String lastname,
                                   String nationalCode,
                                   Long organizationId,
                                   String username,
                                   Boolean accountNonExpired,
                                   Boolean accountNonLocked,
                                   Boolean credentialsNonExpired,
                                   Boolean enabled,
                                   int page, int size, String[] sort);

    Map<String, Object> searchUserOrganizations(long userId, String organizationTitle, int page, int size, String[] sort);

    boolean deleteUserOrganization(long userId, long organizationId) throws Exception;
    boolean addUserOrganizations(long userId, List<Long> organizationIds) throws Exception;

    Map<String, Object> searchUserGroups(long userId, String groupTitle, int page, int size, String[] sort);

    @Transactional
    boolean deleteUserGroup(long userId, long groupId) throws Exception;

    @Transactional
    boolean addUserGroups(long userId, List<Long> groupIds) throws Exception;
}
