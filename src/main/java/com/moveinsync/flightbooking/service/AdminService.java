package com.moveinsync.flightbooking.service;

import com.moveinsync.flightbooking.dto.FlightDto;
import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.FlightSeat;
import com.moveinsync.flightbooking.model.FlightSeatClass;
import com.moveinsync.flightbooking.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private FlightRepo flightRepo;

    public Flight addFlight(Flight flight){
        return flightRepo.save(flight);
    }

    public Flight updateFlight(FlightDto flightDto){
        String flightNumber = flightDto.getFlightNumber();
        Flight originalFlight = flightRepo.findByFlightNumberIgnoreCase(flightNumber);

        String departureAirport = flightDto.getDepartureAirport()==null? originalFlight.getDepartureAirport() :flightDto.getDepartureAirport();
        String arrivalAirport = flightDto.getArrivalAirport()==null?originalFlight.getArrivalAirport():flightDto.getArrivalAirport();
        LocalDateTime departureTime = flightDto.getDepartureTime()==null?originalFlight.getDepartureTime():flightDto.getDepartureTime();
        LocalDateTime arrivalTIme = flightDto.getArrivalTime()==null?originalFlight.getArrivalTime():flightDto.getArrivalTime();
        Date date = flightDto.getDate()==null?originalFlight.getDate():flightDto.getDate();
        Duration duration = flightDto.getFlightDuration()==null?originalFlight.getFlightDuration():flightDto.getFlightDuration();
        Double price = flightDto.getTicketPrice()==null?originalFlight.getTicketPrice():flightDto.getTicketPrice();
        Integer totalSeats = flightDto.getTotalSeats()==null?originalFlight.getTotalSeats():flightDto.getTotalSeats();
        String airline = flightDto.getAirlineName()==null?originalFlight.getAirlineName():flightDto.getAirlineName();
        String aircraft = flightDto.getAircraftType()==null?originalFlight.getAircraftType():flightDto.getAircraftType();
        List<FlightSeatClass> flightSeatClasses = flightDto.getFlightSeatClasses()==null?originalFlight.getFlightSeatClasses():flightDto.getFlightSeatClasses();
        if(flightSeatClasses==null) flightSeatClasses = new ArrayList<>();
        List<FlightSeat> seats = new ArrayList<>();
        while(flightRepo.findByFlightNumberIgnoreCase(flightNumber)!=null){
            flightRepo.deleteByFlightNumberIgnoreCase(flightNumber);
        }
//        flightRepo.deleteByFlightNumberIgnoreCase(flightNumber);
        Flight newFlight = new Flight(flightNumber,departureAirport,departureTime,date,arrivalAirport,arrivalTIme,duration,price,totalSeats,airline,aircraft,flightSeatClasses,seats);
        return addFlight(newFlight);
    }
}
