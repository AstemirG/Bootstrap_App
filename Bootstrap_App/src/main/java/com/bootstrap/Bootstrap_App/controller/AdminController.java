package com.bootstrap.Bootstrap_App.controller;

import com.bootstrap.Bootstrap_App.model.Role;
import com.bootstrap.Bootstrap_App.model.User;
import com.bootstrap.Bootstrap_App.repositories.RoleRepository;
import com.bootstrap.Bootstrap_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;
    private final RoleRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AdminController(UserService service, RoleRepository repository, RoleRepository repository1, PasswordEncoder encoder) {
        this.service = service;
        this.repository = repository1;
        this.encoder = encoder;
    }

    @GetMapping(value = "/all")
    public String showAllUsers(Model model) {
        List<User> allUsers = service.getAllUsers();
        model.addAttribute("allUsers",allUsers);
        return "all-Users";
    }

    @GetMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user",new User());
        return "add-User";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> roleList = user.getRoles();
        //roleList.add(repository.getById(1));
        user.setRoles(roleList);
        service.saveUser(user);
        return "redirect:/admin/all";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user",service.getUserById(id));
        return "edit-User";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        user.setPassword(encoder.encode(user.getPassword()));
        service.updateUser(id,user);
        return "redirect:/admin/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        service.deleteUser(id);
        return "redirect:/admin/all";
    }
}
