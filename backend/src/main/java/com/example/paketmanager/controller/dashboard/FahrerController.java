package com.example.paketmanager.controller.dashboard;

import com.example.paketmanager.dto.dashboard.FahrerDto;
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

    public FahrerController(FahrerService fahrerService) {
        this.fahrerService = fahrerService;
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
    public ResponseEntity<FahrerDto> createFahrer(@RequestBody Fahrer fahrer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fahrerService.saveFahrer(fahrer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FahrerDto> updateFahrer(@PathVariable Long id, @RequestBody Fahrer fahrer) {
        return fahrerService.getFahrerById(id)
                .map(existing -> {
                    fahrer.setFahrerId(id);
                    return ResponseEntity.ok(fahrerService.saveFahrer(fahrer));
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