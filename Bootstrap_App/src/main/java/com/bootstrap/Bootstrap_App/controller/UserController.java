package com.bootstrap.Bootstrap_App.controller;

import com.bootstrap.Bootstrap_App.model.User;
import com.bootstrap.Bootstrap_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/info")
    public String edit(Model model, Principal principal) {
        model.addAttribute("user",service.loadUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/test")
    public String test(Model model,Principal principal) {
        List<User> allUsers = service.getAllUsers();
        model.addAttribute("allUsers",allUsers);
        model.addAttribute("user",service.loadUserByUsername(principal.getName()));
        return "test";
    }
}
