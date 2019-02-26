package com.example.spring_jwt_demo.service;

import com.example.spring_jwt_demo.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserDetailsServiceImpl extends UserDetailsService {
    List<User> findAll();

    void save(User user);
}
