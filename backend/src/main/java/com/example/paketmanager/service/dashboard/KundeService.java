package com.example.paketmanager.service.dashboard;

import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.dto.dashboard.KundeDto;
import com.example.paketmanager.model.Kunde;
import com.example.paketmanager.repository.dashboard.KundeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class KundeService {

    private final KundeRepository kundeRepository;
    private final DtoMapper dtoMapper;

   public KundeService(KundeRepository kundeRepository, DtoMapper dtoMapper) {
        this.kundeRepository = kundeRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<KundeDto> getAllKunden() {
        return kundeRepository.findAll()
                .stream()
                .map(dtoMapper::toKundeDto)
                .collect(Collectors.toList());
    }

    public Optional<KundeDto> getKundeById(Long id) {
        return kundeRepository.findById(id)
                .map(dtoMapper::toKundeDto);
    }

    @Transactional
    public KundeDto saveKunde(Kunde kunde) {
        return dtoMapper.toKundeDto(kundeRepository.save(kunde));
    }

    @Transactional
    public void deleteKunde(Long id) {
        kundeRepository.deleteById(id);
    }
}