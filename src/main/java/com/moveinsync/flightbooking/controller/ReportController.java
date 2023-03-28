package com.moveinsync.flightbooking.controller;

import com.itextpdf.text.DocumentException;
import com.moveinsync.flightbooking.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class ReportController {
    @Autowired
    ReportService reportService;
    @GetMapping("/generatereport")
    public ResponseEntity<InputStreamResource> generatereports() throws DocumentException {

        ByteArrayInputStream bis=reportService.generateReports();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=my-pdf.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
