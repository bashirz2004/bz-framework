package com.bzamani.framework.service.core.action;

import com.bzamani.framework.model.core.action.Action;
import com.bzamani.framework.service.core.IGenericService;

import java.util.List;

public interface IActionService extends IGenericService<Action, Long> {
    List<Action> loadMenuForCurrentUser() throws Exception;
}
