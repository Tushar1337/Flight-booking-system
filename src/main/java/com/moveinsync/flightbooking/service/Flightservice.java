package com.moveinsync.flightbooking.service;

import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.Flightseat;
import com.moveinsync.flightbooking.repository.FlightRepo;
import com.moveinsync.flightbooking.repository.SeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Flightservice {

    @Autowired
    FlightRepo flightRepo;

    @Autowired
    SeatRepo seatRepo;
    public List<Flight> getallflights(){
        return flightRepo.findAll();
    }

    public List<Flightseat> getallseatsbyflightid(Long id){
        return seatRepo.findAllByFlightId(id);
    }

    public String bookaseat(Long id){
        Optional<Flightseat> seat=seatRepo.findById(id);
        if(seat.isPresent()){
            if(seat.get().isBooked()){
                return "This seat is already booked";
            }
            seat.get().setBooked(true);
//            seat.get().setUserId();  //we will try to pass the userid using jwt
//            we have to pass it to payment service before booking the seat
            return "Your seat booked successfully";
        }
        return "Seat id is invalid";
    }
    public String deleteaseat(Long seatid){
        Optional<Flightseat> seat=seatRepo.findById(seatid);

//        we have to check whether the userid match with the userid fetched from seat if(userid got matched with fetched then we will display its not your seat)
//        if matched then display the half amount
        return null;
    }
}
