package com.example.paketmanager.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FahrerDto {

    private Long id;
    private String name;
    private String licenseClass;
    private String phoneNumber;
    private String status; // ex: VERFÜGBAR, IN_TOUR, PAUSE
}