package com.example.paketmanager.controller.dashboard;

import com.example.paketmanager.dto.dashboard.FahrerDto;
import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.model.Fahrer;
import com.example.paketmanager.service.dashboard.FahrerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fahrers")
public class FahrerController {

    private final FahrerService fahrerService;
    private final DtoMapper dtoMapper;

    public FahrerController(FahrerService fahrerService, DtoMapper dtoMapper) {
        this.fahrerService = fahrerService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<FahrerDto> getAllFahrers() {
        return fahrerService.getAllFahrer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FahrerDto> getFahrerById(@PathVariable Long id) {
        return fahrerService.getFahrerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FahrerDto> createFahrer(@RequestBody FahrerDto fahrerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fahrerService.saveFahrer(dtoMapper.toFahrer(fahrerDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FahrerDto> updateFahrer(@PathVariable Long id, @RequestBody FahrerDto fahrerDto) {
        return fahrerService.getFahrerById(id)
                .map(existing -> {
                    fahrerDto.setId(id);
                    return ResponseEntity.ok(fahrerService.saveFahrer(dtoMapper.toFahrer(fahrerDto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFahrer(@PathVariable Long id) {
        if (fahrerService.getFahrerById(id).isPresent()) {
            fahrerService.deleteFahrer(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}