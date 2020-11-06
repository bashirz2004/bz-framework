package com.bzamani.framework.service.security;

import com.bzamani.framework.model.security.User;
import com.bzamani.framework.repository.security.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

  @Autowired
  IUserRepository iUserRepository;

  private Sort.Direction getSortDirection(String direction) {
    if (direction.equals("asc"))
      return Sort.Direction.ASC;
    else if (direction.equals("desc"))
      return Sort.Direction.DESC;
    return Sort.Direction.ASC;
  }

  @Override
  public User findUserByUsernameEquals(String username) {
    return iUserRepository.findUserByUsernameEquals(username);
  }

  @Override
  public User create(User user) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    return iUserRepository.save(user);
  }

  @Override
  public User load(long id) {
    Optional<User> entity = iUserRepository.findById(id);
    if (entity.isPresent())
      return entity.get();
    else
      return null;
  }

  @Override
  public User update(long id, User user) {
    Optional<User> entity = iUserRepository.findById(id);
    if (entity.isPresent()) {
      User _user = entity.get();
      _user.setUsername(user.getUsername());
      _user.setPassword(user.getPassword());
      //_user.setFirstName(user.getFirstName());
      // _user.setLastName(user.getLastName());
      //_user.setActive(user.isActive());
      return iUserRepository.save(_user);
    } else return null;
  }


  @Override
  public void delete(long id) {
    iUserRepository.deleteById(id);
  }


  @Override
  public List<User> getAll(String[] sort) {
    List<Sort.Order> orders = new ArrayList<Sort.Order>();
    if (sort[0].contains(",")) {
      // will sort more than 2 fields
      // sortOrder="field, direction"
      for (String sortOrder : sort) {
        String[] _sort = sortOrder.split(",");
        orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
      }
    } else {
      // sort=[field, direction]
      orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
    }
    return iUserRepository.findAll(Sort.by(orders));
  }


  @Override
  public Map<String, Object> getAllGrid(int page,
                                        int size,
                                        String[] sort) {
    List<Sort.Order> orders = new ArrayList<Sort.Order>();

    if (sort[0].contains(",")) {
      for (String sortOrder : sort) {
        String[] _sort = sortOrder.split(",");
        orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
      }
    } else {
      // sort=[field, direction]
      orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
    }

    List<User> users = new ArrayList<User>();
    Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

    Page<User> pageTuts = iUserRepository.findAll(pagingSort);
    users = pageTuts.getContent();

    Map<String, Object> response = new HashMap<>();
    response.put("entityList", users);
    response.put("currentPage", pageTuts.getNumber());
    response.put("totalRecords", pageTuts.getTotalElements());
    response.put("totalPages", pageTuts.getTotalPages());

    return response;
  }
}
