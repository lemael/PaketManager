package com.example.paketmanager.service.dashboard;

import com.example.paketmanager.dto.dashboard.ColiDto;
import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.model.Coli;
import com.example.paketmanager.model.Kunde;
import com.example.paketmanager.model.Transport;
import com.example.paketmanager.repository.dashboard.ColiRepository;
import com.example.paketmanager.repository.dashboard.KundeRepository;
import com.example.paketmanager.repository.dashboard.TransportRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColiService {

    private final ColiRepository coliRepository;
    private final KundeRepository kundeRepository;
    private final TransportRepository transportRepository;
    private final DtoMapper dtoMapper;

    public ColiService(ColiRepository coliRepository,
                       KundeRepository kundeRepository,
                       TransportRepository transportRepository,
                       DtoMapper dtoMapper) {
        this.coliRepository = coliRepository;
        this.kundeRepository = kundeRepository;
        this.transportRepository = transportRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<ColiDto> getAllColis() {
        return coliRepository.findAll()
                .stream()
                .map(dtoMapper::toColiDto)
                .collect(Collectors.toList());
    }

    public Optional<ColiDto> getColiById(Long id) {
        return coliRepository.findById(id)
                .map(dtoMapper::toColiDto);
    }

    public Optional<ColiDto> getColiByNumber(String coliNumber) {
        return coliRepository.findByColiNumber(coliNumber)
                .map(dtoMapper::toColiDto);
    }

    public List<ColiDto> getColisByStatus(Coli.ColiStatus status) {
        return coliRepository.findByStatus(status)
                .stream()
                .map(dtoMapper::toColiDto)
                .collect(Collectors.toList());
    }

    public List<ColiDto> getColisByKundeId(Long kundeId) {
        return coliRepository.findByKundeId(kundeId)
                .stream()
                .map(dtoMapper::toColiDto)
                .collect(Collectors.toList());
    }
    public List<ColiDto> getColisByTransportId(Long transportId) {
        return coliRepository.findByTransportId(transportId)
                .stream()
                .map(dtoMapper::toColiDto)
                .collect(Collectors.toList());
    }
    public ColiDto saveColi(Coli coli) {
        return dtoMapper.toColiDto(coliRepository.save(coli));
    }

    public ColiDto createColiFromDto(ColiDto dto) {
        Kunde kunde = kundeRepository.findById(dto.getKundeId())
                .orElseThrow(() -> new IllegalArgumentException("Kunde nicht gefunden: " + dto.getKundeId()));

        Coli.ColiBuilder builder = Coli.builder()
                .coliNumber(dto.getColiNumber() != null ? dto.getColiNumber() : "PKG-" + System.currentTimeMillis())
                .recipient(dto.getRecipient())
                .formatAndWeight(dto.getFormatAndWeight())
                .status(dto.getStatus() != null ? Coli.ColiStatus.valueOf(dto.getStatus()) : Coli.ColiStatus.AUSSTEHEND)
                .kunde(kunde);

        if (dto.getTransportId() != null) {
            Transport transport = transportRepository.findById(dto.getTransportId())
                    .orElseThrow(() -> new IllegalArgumentException("Transport nicht gefunden: " + dto.getTransportId()));
            builder.transport(transport);
        }

        return dtoMapper.toColiDto(coliRepository.save(builder.build()));
    }

    public void deleteColi(Long id) {
        coliRepository.deleteById(id);
    }
}