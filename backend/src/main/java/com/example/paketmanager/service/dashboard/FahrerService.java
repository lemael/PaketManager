package com.example.paketmanager.service.dashboard;

import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.dto.dashboard.FahrerDto;
import com.example.paketmanager.model.Fahrer;
import com.example.paketmanager.repository.dashboard.FahrerRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FahrerService {

    private final FahrerRepository fahrerRepository;
    private final DtoMapper dtoMapper;

    public FahrerService(FahrerRepository fahrerRepository, DtoMapper dtoMapper) {
        this.fahrerRepository = fahrerRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<FahrerDto> getAllFahrer() {
        return fahrerRepository.findAll()
                .stream()
                .map(dtoMapper::toFahrerDto)
                .collect(Collectors.toList());
    }

    public Optional<FahrerDto> getFahrerById(Long id) {
        return fahrerRepository.findById(id)
                .map(dtoMapper::toFahrerDto);
    }

    public List<FahrerDto> getFahrerByStatus(Fahrer.FahrerStatus status) {
        return fahrerRepository.findByStatus(status)
                .stream()
                .map(dtoMapper::toFahrerDto)
                .collect(Collectors.toList());
    }

    public FahrerDto saveFahrer(Fahrer fahrer) {
        return dtoMapper.toFahrerDto(fahrerRepository.save(fahrer));
    }

    public void deleteFahrer(Long id) {
        fahrerRepository.deleteById(id);
    }
}