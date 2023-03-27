package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.dto.FlightDto;
import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Map<String, String> add(@RequestBody FlightDto flightDto) {
        Flight flight = new Flight(flightDto.getFlightNumber(), flightDto.getDepartureAirport(), flightDto.getDepartureTime(), flightDto.getDate(), flightDto.getArrivalAirport(), flightDto.getArrivalTime(), flightDto.getFlightDuration(), flightDto.getTicketPrice(), flightDto.getTotalSeats(), flightDto.getAirlineName(), flightDto.getAircraftType(), flightDto.getFlightSeatClasses(), flightDto.getSeats());
        Map<String, String> response = new HashMap<>();
        boolean success = adminService.addFlight(flight);
        if (success) {
            response.put("Status", "Success");
        } else {
            response.put("Status", "Failed");
        }
        return response;
    }

    @DeleteMapping("/delete/{flightNumber}")
    public Map<String, String> delete(@PathVariable String flightNumber) {
        Map<String, String> response = new HashMap<>();
        boolean success = adminService.deleteFlight(flightNumber);
        if (success) {
            response.put("Status", "Success");
        } else {
            response.put("Status", "Failed");
        }
        return response;
    }


    @PutMapping("/update")
    public Map<String, String> update(@RequestBody FlightDto flightDto) {
        Map<String, String> response = new HashMap<>();
        boolean success = adminService.updateFlight(flightDto);
        if (success) {
            response.put("Status", "Success");
        } else {
            response.put("Status", "Failed");
        }
        return response;

    }
}
