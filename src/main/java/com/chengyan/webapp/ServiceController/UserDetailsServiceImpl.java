package com.chengyan.webapp.ServiceController;

import com.chengyan.webapp.ExceptionController.UserNotFoundException;
import com.chengyan.webapp.ModelController.User;
import com.chengyan.webapp.ModelController.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // without authority check
    private final Set<GrantedAuthority> authorities = new HashSet<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.obtainPassword(), getAuthorities());
    }

    private Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
