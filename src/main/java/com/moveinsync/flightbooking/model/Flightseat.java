package com.moveinsync.flightbooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flightseat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name = "seat_number")
    private int seatNumber;

    @Column(name = "booked")
    private boolean booked = false;

    @Column(name = "ticket_price_for_seat")
    private double ticketPrice;
    @Column(name = "user_id")
    private Long userId=null;

    @Column(name = "seat_type")
    @Enumerated(EnumType.STRING)
    private SeatType seatType = SeatType.ECONOMY;


    public Flightseat(Flight flight, int seatNumber, double ticketPrice) {
        this.flight = flight;
        this.seatNumber = seatNumber;
        this.ticketPrice=ticketPrice;
    }

    // Constructor, getters and setters omitted for brevity
}

