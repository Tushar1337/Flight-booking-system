package com.moveinsync.flightbooking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "departure_airport")
    private String departureAirport;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_airport")
    private String arrivalAirport;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "flight_duration")
    private Duration flightDuration;

    @Column(name = "ticket_price")
    private double ticketPrice;

    @Column(name = "available_seats")
    private int availableSeats;

    @Column(name = "airline_name")
    private String airlineName;

    @Column(name = "aircraft_type")
    private String aircraftType;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Flightseat> seats;

    public void generateSeats(int numberOfSeats,double ticketprice) {
        seats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            Flightseat seat = new Flightseat(this, i,ticketprice);
            seats.add(seat);
        }
    }
    // Constructors, getters and setters omitted for brevity
}
