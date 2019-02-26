package com.example.spring_jwt_demo.controller;

import com.example.spring_jwt_demo.models.User;
import com.example.spring_jwt_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String home(){
        return "Welcome to Home";
    }
}
