package com.tahsinProject.demo.controller;

import com.tahsinProject.demo.entity.User;
import com.tahsinProject.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Retrieves a list of all users. Accessible only by admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
            @ApiResponse(responseCode = "404", description = "No users found")
    })
    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getUsers();
        if(users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(404).body("No users found");
        }
    }
}
