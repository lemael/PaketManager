package com.example.paketmanager.controller.dashboard;

import com.example.paketmanager.dto.dashboard.ColiDto;
import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.model.Coli;
import com.example.paketmanager.service.dashboard.ColiService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
public class ColiController {

    private final ColiService coliService;
    private final DtoMapper dtoMapper;

    public ColiController(ColiService coliService, DtoMapper dtoMapper) {
        this.coliService = coliService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<ColiDto> getAllColis() {
        return coliService.getAllColis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColiDto> getColiById(@PathVariable Long id) {
        return coliService.getColiById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ColiDto> createColi(@RequestBody ColiDto coliDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(coliService.createColiFromDto(coliDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColiDto> updateColi(@PathVariable Long id, @RequestBody ColiDto coliDto) {
        return coliService.getColiById(id)
                .map(existing -> {
                    coliDto.setId(id);
                    return ResponseEntity.ok(coliService.saveColi(dtoMapper.toColi(coliDto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColi(@PathVariable Long id) {
        if (coliService.getColiById(id).isPresent()) {
            coliService.deleteColi(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}