package com.bootstrap.Bootstrap_App.controller;

import com.bootstrap.Bootstrap_App.model.Role;
import com.bootstrap.Bootstrap_App.model.User;
import com.bootstrap.Bootstrap_App.repositories.RoleRepository;
import com.bootstrap.Bootstrap_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService service;
    private final PasswordEncoder encoder;
    private final RoleRepository repository;
    @Autowired
    public UserController(UserService service, PasswordEncoder encoder, RoleRepository repository) {
        this.service = service;
        this.encoder = encoder;
        this.repository = repository;
    }

    @GetMapping("/info")
    public String edit(Model model, Principal principal) {
        model.addAttribute("user",service.loadUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/test")
    public String test(Model model,@AuthenticationPrincipal User currentUser) {
        List<User> allUsers = service.getAllUsers();
        model.addAttribute("allUsers",allUsers);
        model.addAttribute("user",currentUser);
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles",repository.findAll());
        return "test";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> roleList = user.getRoles();
        user.setRoles(roleList);
        service.saveUser(user);
        return "redirect:/admin/all";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("userEdit",service.getUserById(id));
        return "test";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user,
                         @PathVariable("id") int id) {
        user.setPassword(encoder.encode(user.getPassword()));
        service.updateUser(id,user);
        return "redirect:/admin/all";
    }
}
