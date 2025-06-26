package com.tahsinProject.demo.controller;


import com.tahsinProject.demo.entity.User;
import com.tahsinProject.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/id/{myId}")
    public User getUserById(@PathVariable ObjectId myId){
        return userService.getUsersById(myId).orElse(null);
    }

    @DeleteMapping("/id/{myId}")
    public boolean deleteUserById(@PathVariable ObjectId myId){
        userService.deleteUserById(myId);
        return true;
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable ObjectId myId){
        User userInDb = userService.getUsersById(myId).orElse(null);
        if(userInDb!=null){
            userInDb.setUserName(newUser.getUserName());
            userInDb.setPassword(newUser.getPassword());
            userService.saveUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
