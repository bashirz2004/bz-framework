package com.bzamani.framework.service.core.user;

import com.bzamani.framework.dto.SelfUserRegistrationDto;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface IUserService extends IGenericService<User, Long> {
    User findUserByUsernameEquals(String username);

    @Transactional
    User saveUserWithSets(User user);

    User selfRegister(SelfUserRegistrationDto userDto);

    @Transactional
    void sendPasswordToUserEmail(String email);

    @Transactional
    String updatePasswordOfUserByMobile(String mobile, String newPassword) throws URISyntaxException;

    @Transactional
    boolean changePasswordByAdmin(Long userId, String newPassword);

    @Transactional
    void changeAuthenticatedUserPassword(String oldPassword, String newPassword);

    Map<String, Object> searchUser(String firstname,
                                   String lastname,
                                   String nationalCode,
                                   String mobile,
                                   Long organizationId,
                                   String username,
                                   Boolean accountNonExpired,
                                   Boolean accountNonLocked,
                                   Boolean credentialsNonExpired,
                                   Boolean enabled,
                                   int page, int size, String[] sort);

    Map<String, Object> searchUserOrganizations(long userId, String organizationTitle, int page, int size, String[] sort);

    boolean deleteUserOrganization(long userId, long organizationId);

    boolean addUserOrganizations(long userId, List<Long> organizationIds);

    Map<String, Object> searchUserGroups(long userId, String groupTitle, int page, int size, String[] sort);

    @Transactional
    boolean deleteUserGroup(long userId, long groupId);

    @Transactional
    boolean addUserGroups(long userId, List<Long> groupIds);

    @Transactional
    void resetWrongPasswordTries(Long userId);

    @Transactional
    void increaseWrongPasswordTries(Long userId);

    @Transactional
    void lock(Long userId);
}
