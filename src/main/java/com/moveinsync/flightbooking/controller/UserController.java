package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.dto.FlightDto;
import com.moveinsync.flightbooking.dto.SearchDto;
import com.moveinsync.flightbooking.exceptions.FlightAuthException;
import com.moveinsync.flightbooking.exceptions.UsernamePasswordException;
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
import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/")
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
        userService.usernameExists(userDto.getUsername());
        userService.registerUser(userDto);
        return ResponseEntity.ok().body("User registered successfully");
    }

    @GetMapping("/users")
    public List<User> showallusers(@RequestHeader Map request) {
        String token = request.get("authorization").toString().substring(7);
        return userService.showall(token);
    }

    @PostMapping("/login")
    public String login(@RequestBody User userdto) throws UsernamePasswordException {
        System.out.println(userdto);
        return (userService.loginUser(userdto));
    }

    @PutMapping("/update")
    public String update(@RequestBody User userdto, @RequestHeader Map request) throws UsernamePasswordException {
        String token = request.get("authorization").toString().substring(7);
        System.out.println(userdto);
        return (userService.updateUser(userdto, token));
    }


    @PostMapping("/flight-with-class")
    List<Flight> getFlightWithClass(@RequestBody SearchDto searchDto) {
        Date date1 = java.sql.Date.valueOf(searchDto.getDate());
        return userFlightService.findFlightWithClass(date1, searchDto.getSource(), searchDto.getDestination(), searchDto.getSeatType());
    }
}
