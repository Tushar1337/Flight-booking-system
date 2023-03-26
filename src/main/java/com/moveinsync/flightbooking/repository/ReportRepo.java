package com.moveinsync.flightbooking.repository;

import com.moveinsync.flightbooking.model.Reportmodel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Reportmodel,Long> {
    Reportmodel findByFlightId(Long flight_Id);
}
