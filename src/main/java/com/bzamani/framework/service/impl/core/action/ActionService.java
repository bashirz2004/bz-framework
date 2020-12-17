package com.bzamani.framework.service.impl.core.action;

import com.bzamani.framework.common.utility.SecurityUtility;
import com.bzamani.framework.model.core.action.Action;
import com.bzamani.framework.repository.core.action.IActionRepository;
import com.bzamani.framework.service.impl.core.GenericService;
import com.bzamani.framework.service.core.action.IActionService;
import com.bzamani.framework.service.core.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
