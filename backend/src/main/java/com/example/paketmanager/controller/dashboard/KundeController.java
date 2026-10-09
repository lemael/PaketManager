package com.example.paketmanager.controller.dashboard;

import com.example.paketmanager.dto.dashboard.KundeDto;
import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.model.Kunde;
import com.example.paketmanager.service.dashboard.KundeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kunden")
public class KundeController {

    private final KundeService kundeService;
    private final DtoMapper dtoMapper;

    public KundeController(KundeService kundeService, DtoMapper dtoMapper) {
        this.kundeService = kundeService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<KundeDto> getAllKunden() {
        return kundeService.getAllKunden();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KundeDto> getKundeById(@PathVariable Long id) {
        return kundeService.getKundeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<KundeDto> createKunde(@RequestBody KundeDto kundeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(kundeService.saveKunde(dtoMapper.toKunde(kundeDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KundeDto> updateKunde(@PathVariable Long id, @RequestBody KundeDto kundeDto) {
        return kundeService.getKundeById(id)
                .map(existing -> {
                    kundeDto.setId(id);
                    return ResponseEntity.ok(kundeService.saveKunde(dtoMapper.toKunde(kundeDto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKunde(@PathVariable Long id) {
        if (kundeService.getKundeById(id).isPresent()) {
            kundeService.deleteKunde(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}