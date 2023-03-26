package com.moveinsync.flightbooking.repository;

import com.moveinsync.flightbooking.model.Flightseat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Flightseat,Long> {
    @Query("SELECT s FROM Flightseat s WHERE s.flight.id = :flightId")
    List<Flightseat> findAllByFlightId(@Param("flightId") Long flightId);
}
