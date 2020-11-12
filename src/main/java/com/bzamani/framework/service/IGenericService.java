package com.bzamani.framework.service;

import com.bzamani.framework.model.security.User;

import java.util.List;
import java.util.Map;

public interface IGenericService<T, PK> {
  T save(T t);

  T loadByEntityId(PK id);

  boolean deleteByEntityId(PK id);

  List<T> getAll(String[] sort);

  Map<String, Object> getAllGrid(int page,
                                 int size,
                                 String[] sort);
}
