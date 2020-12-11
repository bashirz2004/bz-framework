package com.bzamani.framework.service.security.user;

import com.bzamani.framework.model.security.Action;
import com.bzamani.framework.model.security.User;
import com.bzamani.framework.repository.security.IUserRepository;
import com.bzamani.framework.service.GenericService;
import com.bzamani.framework.service.security.action.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService extends GenericService<User, Long> implements IUserService {

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IActionService iActionService;

    @Override
    protected JpaRepository<User, Long> getGenericRepo() {
        return iUserRepository;
    }

    @Override
    public User findUserByUsernameEquals(String username) {
        return iUserRepository.findUserByUsernameEquals(username);
    }


    @Override
    @Transactional
    public User registerUserByHimself(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Action> actions = new HashSet<>();
        actions.add(iActionService.loadByEntityId(3L));//patient
        user.setActions(actions);
        return iUserRepository.save(user);
    }

    @Override
    public List<User> getAllAuthorized(){
        return iUserRepository.getAllAuthorized();
    }

}
