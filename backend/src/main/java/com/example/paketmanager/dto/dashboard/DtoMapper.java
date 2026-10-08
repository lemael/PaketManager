package com.example.paketmanager.dto.dashboard;

import com.example.paketmanager.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public KundeDto toKundeDto(Kunde kunde) {
        if (kunde == null) return null;
        return KundeDto.builder()
                .id(kunde.getId())
                .name(kunde.getName())
                .mainContact(kunde.getMainContact())
                .city(kunde.getCity())
                .contract(kunde.getContract())
                .monthlyVolume(kunde.getMonthlyVolume())
                .totalColisCount(kunde.getColis() != null ? kunde.getColis().size() : 0)
                .build();
    }

    public AutoDto toAutoDto(Auto auto) {
        if (auto == null) return null;
        return AutoDto.builder()
                .id(auto.getId())
                .name(auto.getName())
                .plateNumber(auto.getPlateNumber())
                .model(auto.getModel())
                .maxCapacity(auto.getMaxCapacity())
                .mileage(auto.getMileage())
                .tuvInspection(auto.getTuvInspection())
                .status(auto.getStatus() != null ? auto.getStatus().name() : null)
                .build();
    }

    public FahrerDto toFahrerDto(Fahrer fahrer) {
        if (fahrer == null) return null;
        return FahrerDto.builder()
                .id(fahrer.getId())
                .name(fahrer.getName())
                .licenseClass(fahrer.getLicenseClass())
                .phoneNumber(fahrer.getPhoneNumber())
                .status(fahrer.getStatus() != null ? fahrer.getStatus().name() : null)
                .build();
    }

    public TransportDto toTransportDto(Transport transport) {
        if (transport == null) return null;
        return TransportDto.builder()
                .id(transport.getId())
                .transportNumber(transport.getTransportNumber())
                .zone(transport.getZone())
                .colisCount(transport.getColisCount())
                .deliveredCount(transport.getDeliveredCount())
                .status(transport.getStatus() != null ? transport.getStatus().name() : null)
                .fahrerId(transport.getFahrer() != null ? transport.getFahrer().getId() : null)
                .fahrerName(transport.getFahrer() != null ? transport.getFahrer().getName() : null)
                .autoId(transport.getAuto() != null ? transport.getAuto().getId() : null)
                .autoName(transport.getAuto() != null ? transport.getAuto().getName() : null)
                .autoPlateNumber(transport.getAuto() != null ? transport.getAuto().getPlateNumber() : null)
                .coliIds(transport.getColis() != null ? 
                        transport.getColis().stream().map(Coli::getId).collect(Collectors.toList()) : null)
                .build();
    }

    public ColiDto toColiDto(Coli coli) {
        if (coli == null) return null;
        return ColiDto.builder()
                .id(coli.getId())
                .coliNumber(coli.getColiNumber())
                .status(coli.getStatus() != null ? coli.getStatus().name() : null)
                .kundeName(coli.getKunde() != null ? coli.getKunde().getName() : null)
                .transportZone(coli.getTransport() != null ? coli.getTransport().getZone() : null)
                .build();
    }
}