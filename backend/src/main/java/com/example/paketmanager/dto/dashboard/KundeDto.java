package com.example.paketmanager.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KundeDto {

    private Long id;
    private String name;
    private String mainContact;
    private String city;
    private String contract;
    private String monthlyVolume;
    private int totalColisCount; // Utile pour afficher le total dans l'UI sans charger toute la liste
}