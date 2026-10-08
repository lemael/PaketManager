package com.example.paketmanager.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoDto {

    private Long id;
    private String name;
    private String plateNumber;
    private String model;
    private String maxCapacity;
    private String mileage;
    private String tuvInspection;
    private String status;
}