package com.tahsinProject.demo.controller;


import com.tahsinProject.demo.entity.User;
import com.tahsinProject.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/id/{myId}")
    public User getUserById(@PathVariable ObjectId myId){
        return userService.getUsersById(myId).orElse(null);
    }

    @DeleteMapping
    public boolean deleteUserByName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.deleteByUserName(authentication.getName());
//        userService.deleteUserById(myId);
//        return true;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInDb = userService.findByUserName(authentication.getName());
        if(userInDb!=null){
            userInDb.setUserName(newUser.getUserName());
            userInDb.setPassword(newUser.getPassword());
            userService.saveUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
