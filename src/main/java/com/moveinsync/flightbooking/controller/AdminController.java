package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.dto.FlightDto;
import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Flight add(@RequestBody FlightDto flightDto){
        Flight flight = new Flight(flightDto.getFlightNumber(),flightDto.getDepartureAirport(),flightDto.getDepartureTime(),flightDto.getDate(),flightDto.getArrivalAirport(),flightDto.getArrivalTime(),flightDto.getFlightDuration(),flightDto.getTicketPrice(),flightDto.getTotalSeats(),flightDto.getAirlineName(),flightDto.getAircraftType(),flightDto.getFlightSeatClasses(),flightDto.getSeats());
        return adminService.addFlight(flight);
    }

    @PutMapping("/add")
    public Flight update(@RequestBody FlightDto flightDto){
        return adminService.updateFlight(flightDto);
    }
}
