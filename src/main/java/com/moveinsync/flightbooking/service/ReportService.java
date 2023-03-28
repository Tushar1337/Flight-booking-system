package com.moveinsync.flightbooking.service;

import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.Reportmodel;
import com.moveinsync.flightbooking.repository.FlightRepo;
import com.moveinsync.flightbooking.repository.ReportRepo;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    ReportRepo reportRepo;

    @Autowired
    FlightRepo flightRepo;


    public String generateReports(String flightNumber){
        Flight flight=flightRepo.findByFlightNumberIgnoreCase(flightNumber);
        if(flight != null){
            Reportmodel report=reportRepo.findByFlightNumber(flightNumber);
            return "Your flight with flightNumber "+flight.getFlightNumber()+" has generated a revenue of "+report.getRevenueGenerated()+" with the booked seats are "+report.getBookedseats();
        }
        return "This flight doesn't exist";
    }
}
