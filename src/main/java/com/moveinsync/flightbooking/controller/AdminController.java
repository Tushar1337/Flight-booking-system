package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.dto.FlightDto;
import com.moveinsync.flightbooking.exceptions.InvalidFlightException;
import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> add(@RequestBody FlightDto flightDto) throws InvalidFlightException {
        try {
            Flight flight = new Flight(flightDto.getFlightNumber(), flightDto.getDepartureAirport(), flightDto.getDepartureTime(), flightDto.getDate(), flightDto.getArrivalAirport(), flightDto.getArrivalTime(), flightDto.getFlightDuration(), flightDto.getTicketPrice(), flightDto.getTotalSeats(), flightDto.getAirlineName(), flightDto.getAircraftType(), flightDto.getFlightSeatClasses(), flightDto.getSeats());
            boolean success = adminService.addFlight(flight);
            Map<String, String> response = new HashMap<>();
            if (success) {
                response.put("Status", "Success");
            } else {
                response.put("Status", "Failed");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            throw new InvalidFlightException("Invalid flight data");
        }
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
