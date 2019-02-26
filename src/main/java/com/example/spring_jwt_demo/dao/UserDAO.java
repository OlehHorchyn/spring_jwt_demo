package com.example.spring_jwt_demo.dao;

import com.example.spring_jwt_demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    //CRUD

    User findByUsername(String username);
}
