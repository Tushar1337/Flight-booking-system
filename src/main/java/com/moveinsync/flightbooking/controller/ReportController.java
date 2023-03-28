package com.moveinsync.flightbooking.controller;

import com.moveinsync.flightbooking.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @GetMapping("/generatereport/{flightNumber}")
    public String generatereports(@PathVariable("flightNumber") String  flightNumber){
        return reportService.generateReports(flightNumber);
    }
}
