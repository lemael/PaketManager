package com.example.paketmanager.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportDto {

    private Long id;
    private String transportNumber;
    private String zone;
    private Integer colisCount;
    private Integer deliveredCount;
    private String status;

    // Informations simplifiées pour le chauffeur (pas d'objet complet)
    private Long fahrerId;
    private String fahrerName;

    // Informations simplifiées pour le véhicule
    private Long autoId;
    private String autoName;
    private String autoPlateNumber;

    // Liste des IDs de colis ou résumé minimal
    private List<Long> coliIds;
}