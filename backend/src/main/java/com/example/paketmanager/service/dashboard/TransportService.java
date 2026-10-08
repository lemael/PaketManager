package com.example.paketmanager.service.dashboard;

import com.example.paketmanager.model.Transport;
import com.example.paketmanager.repository.dashboard.TransportRepository;

import org.springframework.stereotype.Service;

import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.dto.dashboard.TransportDto;

import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TransportService {

    private final TransportRepository transportRepository;
    private final DtoMapper dtoMapper;

    public TransportService(TransportRepository transportRepository, DtoMapper dtoMapper) {
        this.transportRepository = transportRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<TransportDto> getAllTransports() {
        return transportRepository.findAll()
                .stream()
                .map(dtoMapper::toTransportDto)
                .collect(Collectors.toList());
    }

    public Optional<TransportDto> getTransportById(Long id) {
        return transportRepository.findById(id)
                .map(dtoMapper::toTransportDto);
    }

    public Optional<TransportDto> getTransportByNumber(String transportNumber) {
        return transportRepository.findByTransportNumber(transportNumber)
                .map(dtoMapper::toTransportDto);
    }

    public List<TransportDto> getTransportsByStatus(Transport.TransportStatus status) {
        return transportRepository.findByStatus(status)
                .stream()
                .map(dtoMapper::toTransportDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransportDto saveTransport(Transport transport) {
        return dtoMapper.toTransportDto(transportRepository.save(transport));
    }

    @Transactional
    public void deleteTransport(Long id) {
        transportRepository.deleteById(id);
    }
}