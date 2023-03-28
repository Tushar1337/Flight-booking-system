package com.moveinsync.flightbooking.service;

import com.moveinsync.flightbooking.model.Flight;
import com.moveinsync.flightbooking.model.FlightSeat;
import com.moveinsync.flightbooking.repository.FlightRepo;
import com.moveinsync.flightbooking.repository.SeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
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



    private static final long MAX_PRICE_INCREASE_PERCENTAGE = 50;
    private static final long DAYS_BEFORE_DEPARTURE_TO_MAX_PRICE_INCREASE = 60;
    private static final long MAX_MINUTES_BEFORE_CANCEL_TICKET = 240;

    public Double calculateTicketPrice(long flightId,double ticketPrice) {

        long numUnsoldSeats = seatRepo.getNumUnsoldSeatsForFlight(flightId);
        double BASE_TICKET_PRICE = ticketPrice;
        Flight flight = flightRepo.findById(flightId);
        LocalDate currentDate = LocalDate.now();
        long daysUntilDeparture = ChronoUnit.DAYS.between((Temporal) currentDate, (Temporal) flight.getDate().toLocalDate()); // Implement this method to calculate days until departure


        Double priceIncreasePercentage = 0.0;
        if (numUnsoldSeats > 0) {
            priceIncreasePercentage = (100.0 * (MAX_PRICE_INCREASE_PERCENTAGE * numUnsoldSeats)) / (numUnsoldSeats + 10);
            if (priceIncreasePercentage > MAX_PRICE_INCREASE_PERCENTAGE) {
                priceIncreasePercentage = MAX_PRICE_INCREASE_PERCENTAGE * 1.0;
            }
        }
        if (daysUntilDeparture <= DAYS_BEFORE_DEPARTURE_TO_MAX_PRICE_INCREASE) {
            long daysUntilMaxPriceIncrease = DAYS_BEFORE_DEPARTURE_TO_MAX_PRICE_INCREASE - daysUntilDeparture;
            long maxPriceIncreasePercentage = (daysUntilMaxPriceIncrease / 7) * 10;
            priceIncreasePercentage += maxPriceIncreasePercentage;
        }
        return BASE_TICKET_PRICE + (BASE_TICKET_PRICE * priceIncreasePercentage / 100);
    }

    public List<Flight> getallflights(){
        List<Flight> flightList = flightRepo.findAll();
        flightList.stream().forEach((flight)->{
            flight.getSeats().forEach((seat)->{
                seat.setTicketPrice(calculateTicketPrice(flight.getId(), seat.getTicketPrice()));
            });
        });
        return flightList;
    }

    public List<FlightSeat> getallseatsbyflightid(Long id){
        List<FlightSeat> flightSeatList = seatRepo.findAllByFlightId(id);
        flightSeatList.forEach((seat)->{
            seat.setTicketPrice(calculateTicketPrice(id, seat.getTicketPrice()));
        });
        return flightSeatList;
    }

    public String bookaseat(Long id){
        Long user_Id=123L;
        Optional<FlightSeat> seat=seatRepo.findById(id);
        if(seat.isPresent()){
            if(seat.get().isBooked()){
                return "This seat is already booked";
            }
            seat.get().setBooked(true);
            Long flightId=seat.get().getFlight().getId();
            Double ticketprice = calculateTicketPrice(seat.get().getFlight().getId(), seat.get().getTicketPrice());
            paymentservice.dopayment(seat.get().getFlight().getFlightNumber(),ticketprice);
            seat.get().setUserId(user_Id);
            seatRepo.save(seat.get());
            return "Your seat booked successfully";
        }
        return "Seat id is invalid";
    }

    public String deleteaseat(Long seatid){
        Long user_Id=123L;
        Optional<FlightSeat> seat=seatRepo.findById(seatid);

        if(seat.isPresent() &&user_Id.equals(seat.get().getUserId())){
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime flightDate = seat.get().getFlight().getArrivalTime();

            Duration duration = Duration.between(currentDate, flightDate);
            long minutes = Math.abs(duration.toMinutes());

            if(MAX_MINUTES_BEFORE_CANCEL_TICKET>=minutes){
                return "You can't cancel seat...";
            }

            seat.get().setUserId(null);
            seat.get().setBooked(false);
            seatRepo.save(seat.get());
            double refunded_ticket_price=(seat.get().getTicketPrice())/2;
            return "Seat deleted successfully your refunded amount is "+refunded_ticket_price;
        }
        return "Its not Your seat Check your seat number first";
    }
}
