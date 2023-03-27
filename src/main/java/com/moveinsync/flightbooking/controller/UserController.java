package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.dto.FlightDto;
import com.moveinsync.flightbooking.exceptions.FlightAuthException;
import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.SeatType;
import com.moveinsync.flightbooking.model.User;
import com.moveinsync.flightbooking.repository.FlightRepo;
import com.moveinsync.flightbooking.repository.UserRepo;
import com.moveinsync.flightbooking.service.UserFlightService;
import com.moveinsync.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFlightService userFlightService;

    @Autowired
    private FlightRepo flightRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userDto) throws FlightAuthException {
        if (userService.usernameExists(userDto.getUsername()) != null) {
            throw new FlightAuthException("Username is already taken");
        }
        userService.registerUser(userDto);

        return ResponseEntity.ok().body("User registered successfully");
    }
    @GetMapping("/users")
    public List<User> showallusers(@RequestHeader Map request) {
        String token = request.get("authorization").toString().substring(7);
        System.out.println(token);
        return userService.showall(token);
    }

    @PostMapping("/login")
    public String login(@RequestBody User userdto) {
        return (userService.loginUser(userdto));
    }


    @GetMapping("/flight")
    List<Flight> getFlight(@RequestParam String date, @RequestParam String source, @RequestParam String destination, @RequestParam SeatType seatType) {
        Date date1 = java.sql.Date.valueOf(date);
        return userFlightService.findFlightWithClass(date1, source, destination,seatType);
    }


}
