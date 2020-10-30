package com.bzamani.framework.service.core.user;

import com.bzamani.framework.model.core.user.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
  User create(User user);

  User load(long id);

  User update(long id, User user);

  void delete(long id);

  List<User> getAll(String[] sort);

  Map<String, Object> getAllGrid(int page,
                                 int size,
                                 String[] sort);


}
