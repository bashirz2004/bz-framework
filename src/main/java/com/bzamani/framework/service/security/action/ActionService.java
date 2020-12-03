package com.bzamani.framework.service.security.action;

import com.bzamani.framework.model.security.Action;
import com.bzamani.framework.repository.security.IActionRepository;
import com.bzamani.framework.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ActionService extends GenericService<Action, Long> implements IActionService {

    @Autowired
    IActionRepository iActionRepository;

    @Override
    protected JpaRepository<Action, Long> getGenericRepo() {
        return iActionRepository;
    }

}
