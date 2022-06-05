package com.blog.webapp.security;

import com.blog.webapp.daos.UserDao;
import com.blog.webapp.exceptions.ResourceNotFoundException;
import com.blog.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userDao.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", username));

        return user;
    }
}
