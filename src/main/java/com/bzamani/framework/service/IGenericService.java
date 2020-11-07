package com.bzamani.framework.service;

import com.bzamani.framework.model.security.User;

import java.util.List;
import java.util.Map;

public interface IGenericService<T, PK> {
  T create(T t);

  T load(PK id);

  T update(T t);

  void delete(PK id);

  List<T> getAll(String[] sort);

  Map<String, Object> getAllGrid(int page,
                                 int size,
                                 String[] sort);
}
