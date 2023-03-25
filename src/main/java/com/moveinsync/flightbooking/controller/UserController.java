package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.dto.FlightDto;
import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.SeatType;
import com.moveinsync.flightbooking.model.User;
import com.moveinsync.flightbooking.repository.FlightRepo;
import com.moveinsync.flightbooking.service.UserFlightService;
import com.moveinsync.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFlightService userFlightService;

    @Autowired
    private FlightRepo flightRepo;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userDto) {
        if (userService.usernameExists(userDto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        userService.registerUser(userDto);

        return ResponseEntity.ok().body("User registered successfully");
    }
    @GetMapping("/users")
    public List<User> showallusers(@RequestHeader HashMap request) {
        String token = request.get("authorization").toString();
        System.out.println(token);
        return userService.showall();
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


    @PostMapping("/add")
    public void add(@RequestBody FlightDto flightDto){
        Flight flight = new Flight(flightDto.getFlightNumber(),flightDto.getDepartureAirport(),flightDto.getDepartureTime(),flightDto.getDate(),flightDto.getArrivalAirport(),flightDto.getArrivalTime(),flightDto.getFlightDuration(),flightDto.getTicketPrice(),flightDto.getTotalSeats(),flightDto.getAirlineName(),flightDto.getAircraftType(),flightDto.getFlightSeatClasses(),flightDto.getSeats());
        flightRepo.save(flight);
    }
}

//{
//    "date":"2023-03-24",
//    "source":"Indira Gandhi International Airport",
//    "destination":"Bangalore International Airport Limited Airport"
//}