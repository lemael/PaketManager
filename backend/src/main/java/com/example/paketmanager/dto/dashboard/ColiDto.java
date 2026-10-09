package com.example.paketmanager.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColiDto {
    private Long id;
    private String coliNumber;
    private String recipient;
    private String formatAndWeight;
    private String status;
    private Long kundeId;
    private String kundeName;     // Directement le nom "DHL Express"
    private Long transportId;
    private String transportZone; // Directement la zone "Dortmund"
}