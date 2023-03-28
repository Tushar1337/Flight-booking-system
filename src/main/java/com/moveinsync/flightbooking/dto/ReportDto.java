package com.moveinsync.flightbooking.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
public class ReportDto {
        private int Bookedseats;

        public ReportDto(int bookedseats, Double revenueGenerated, String flightNumber) {
                Bookedseats = bookedseats;
                this.revenueGenerated = revenueGenerated;
                this.flightNumber = flightNumber;
        }

        private Double revenueGenerated;

        private String flightNumber;
}
