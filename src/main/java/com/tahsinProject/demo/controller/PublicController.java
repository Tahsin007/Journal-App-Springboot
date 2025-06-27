package com.tahsinProject.demo.controller;

import com.tahsinProject.demo.entity.User;
import com.tahsinProject.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }


}
