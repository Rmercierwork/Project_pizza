package fr.epsi.rennes.poec.raphael.pizza.controller;

import fr.epsi.rennes.poec.raphael.pizza.domain.User;
import fr.epsi.rennes.poec.raphael.pizza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public void addUser(User user) {
        userService.addUser(user);
    }
}