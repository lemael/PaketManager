package com.example.paketmanager.service.dashboard;

import com.example.paketmanager.dto.dashboard.AutoDto;
import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.model.Auto;
import com.example.paketmanager.repository.dashboard.AutoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutoService {

    private final AutoRepository autoRepository;
    private final DtoMapper dtoMapper;

    public AutoService(AutoRepository autoRepository, DtoMapper dtoMapper) {
        this.autoRepository = autoRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<AutoDto> getAllAutos() {
        return autoRepository.findAll()
                .stream()
                .map(dtoMapper::toAutoDto)
                .collect(Collectors.toList());
    }

    public Optional<AutoDto> getAutoById(Long id) {
        return autoRepository.findById(id)
                .map(dtoMapper::toAutoDto);
    }

    public List<AutoDto> getAutosByStatus(Auto.AutoStatus status) {
        return autoRepository.findByStatus(status)
                .stream()
                .map(dtoMapper::toAutoDto)
                .collect(Collectors.toList());
    }

    public AutoDto saveAuto(Auto auto) {
        return dtoMapper.toAutoDto(autoRepository.save(auto));
    }

    public void deleteAuto(Long id) {
        autoRepository.deleteById(id);
    }
}