package com.bzamani.framework.common.config.security;

import com.bzamani.framework.model.core.action.Action;
import com.bzamani.framework.model.core.group.Group;
import com.bzamani.framework.repository.core.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.bzamani.framework.model.core.user.User user = iUserRepository.findUserByUsernameEquals(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found with the name " + username);
        Set<GrantedAuthority> authorities = new HashSet<>();
        Set<Action> actionsUnion = new HashSet<>();
        for (Group group : user.getGroups())
            actionsUnion.addAll(group.getActions());

        for (Action action : actionsUnion)
            authorities.add(new SimpleGrantedAuthority("ROLE_" + action.getCode()));

        user.setAuthorities(authorities);
        return new User(username, user.getPassword(), authorities);
    }

}
