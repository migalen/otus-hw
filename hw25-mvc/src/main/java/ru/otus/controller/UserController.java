package ru.otus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.model.User;
import ru.otus.service.db.UserService;

import java.util.List;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping({"/", "/users"})
    public String usersListView(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("users", userList);
        return "users.html";
    }

    @PostMapping({"/addUser"})
    public RedirectView addUser(@ModelAttribute User user) {
        userService.save(user);
        List<User> userList = userService.findAll();
        return new RedirectView("/users", true);
    }
}

