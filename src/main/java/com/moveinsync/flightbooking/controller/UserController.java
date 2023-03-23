package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.model.User;
import com.moveinsync.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userDto) {
        if (userService.usernameExists(userDto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

//        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.getAdmin);
        userService.registerUser(userDto);

        return ResponseEntity.ok().body("User registered successfully");
    }
    @GetMapping("/users")
    public List<User> showallusers() {
        return userService.showall();
    }

    @PostMapping("/login")
    public String login(@RequestBody User userdto) {
        return (userService.loginUser(userdto));
    }
}
