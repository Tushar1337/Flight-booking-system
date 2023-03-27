package com.moveinsync.flightbooking.service;

import com.moveinsync.flightbooking.configuration.JwtUtil;
import com.moveinsync.flightbooking.configuration.PasswordUtil;
import com.moveinsync.flightbooking.model.User;
import com.moveinsync.flightbooking.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtUtil jwtUtil;

    public User usernameExists(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> showall(String token) {
        System.out.println(jwtUtil.validateToken(token));
        return userRepo.findAll();
    }
    public String save(User user) {
        userRepo.save(user);
        return ("Saved");
    }

    public User registerUser(User userDto) {

        // save the user to the database
        userDto.setPassword(PasswordUtil.encode(userDto.getPassword()));
        User savedUser = userRepo.save(userDto);

        // generate a JWT token
        String token = jwtUtil.generateToken(savedUser);
        System.out.println(token);

        // set the JWT token on the user object
        savedUser.setToken(token);

        return savedUser;
    }

    public String loginUser(User userDto) {

        String curusername = userDto.getUsername();

        User curuser = userRepo.findByUsername(curusername);

        System.out.println(PasswordUtil.match(userDto.getPassword(),curuser.getPassword()));

        if (PasswordUtil.match(userDto.getPassword(),curuser.getPassword())) {
            // generate a JWT token
            String token = jwtUtil.generateToken(curuser);

            // set the JWT token on the user object
            userDto.setToken(token);

            return token;
        }
        return null;
    }
}
