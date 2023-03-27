package com.moveinsync.flightbooking.service;

import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.FlightSeat;
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

    @Autowired
    Paymentservice paymentservice;

    public List<Flight> getallflights() {
        return flightRepo.findAll();
    }

    public List<FlightSeat> getallseatsbyflightid(Long id) {
        return seatRepo.findAllByFlightId(id);
    }

    public String bookaseat(Long id) {
        Long user_Id = 123L;
        Optional<FlightSeat> seat = seatRepo.findById(id);
        if (seat.isPresent()) {
            if (seat.get().isBooked()) {
                return "This seat is already booked";
            }
            String flightNumber = seat.get().getFlight().getFlightNumber();
            Double ticketprice = seat.get().getTicketPrice();
            paymentservice.dopayment(flightNumber, ticketprice);
            seat.get().setBooked(true);
            seat.get().setUserId(user_Id);
            seatRepo.save(seat.get());
            return "Your seat booked successfully";
        }
        return "Seat id is invalid";
    }

    public String deleteaseat(Long seatid) {
        Long user_Id = 123L;
        Optional<FlightSeat> seat = seatRepo.findById(seatid);
        if (seat.isPresent() && user_Id.equals(seat.get().getUserId())) {
            seat.get().setUserId(null);
            seat.get().setBooked(false);
            seatRepo.save(seat.get());
            double refunded_ticket_price = (seat.get().getTicketPrice()) / 2;
            return "Seat deleted successfully your refunded amount is " + refunded_ticket_price;
        }
        return "Its not Your seat Check your seat number first";
    }
}
