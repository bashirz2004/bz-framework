package com.bzamani.framework.service.security.action;

import com.bzamani.framework.model.security.Action;
import com.bzamani.framework.service.IGenericService;

import java.util.List;

public interface IActionService extends IGenericService<Action, Long> {
    List<Action> loadMenuForCurrentUser();
}
