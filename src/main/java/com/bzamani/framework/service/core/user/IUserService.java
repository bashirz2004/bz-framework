package com.bzamani.framework.service.core.user;

import com.bzamani.framework.dto.UserQuickRegistrationDto;
import com.bzamani.framework.model.core.user.User;
import com.bzamani.framework.service.core.IGenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface IUserService extends IGenericService<User, Long> {
    User findUserByUsernameEquals(String username);

    User selfRegister(UserQuickRegistrationDto userDto) throws Exception;

    @Transactional
    void sendPasswordToUserEmail(String email) throws Exception;

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
}
