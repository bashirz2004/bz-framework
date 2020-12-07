package com.bzamani.framework.service.security.action;

import com.bzamani.framework.config.security.SecurityUtility;
import com.bzamani.framework.model.security.Action;
import com.bzamani.framework.repository.security.IActionRepository;
import com.bzamani.framework.service.GenericService;
import com.bzamani.framework.service.security.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService extends GenericService<Action, Long> implements IActionService {

    @Autowired
    IActionRepository iActionRepository;

    @Autowired
    IUserService iUserService;

    @Override
    protected JpaRepository<Action, Long> getGenericRepo() {
        return iActionRepository;
    }

    @Override
    public List<Action> loadMenuForCurrentUser() {
        return iActionRepository.loadMenuForCurrentUser(iUserService.findUserByUsernameEquals(SecurityUtility.getAuthenticatedUser().getUsername()).getId());
    }

}
