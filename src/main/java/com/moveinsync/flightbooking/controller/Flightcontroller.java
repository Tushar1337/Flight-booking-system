package com.moveinsync.flightbooking.controller;


import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.FlightSeat;
import com.moveinsync.flightbooking.service.Flightservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/flights/")
public class Flightcontroller {

    @Autowired
    Flightservice flightservice;

    @GetMapping("/getallflights")
    public List<Flight> getallflights() {
        return flightservice.getallflights();
    }

    @GetMapping("/{flightId}/")
    public List<FlightSeat> getallflightsbyid(@PathVariable("flightId") Long flightid) {
        return flightservice.getallseatsbyflightid(flightid);
    }

    @PostMapping("/{seatid}")
    public String booktheseat(@PathVariable("seatid") Long seatid) {
        return flightservice.bookaseat(seatid);
    }
}
