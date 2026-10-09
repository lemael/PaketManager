package com.example.paketmanager.controller.dashboard;

import com.example.paketmanager.dto.dashboard.AutoDto;
import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.model.Auto;
import com.example.paketmanager.service.dashboard.AutoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
public class AutoController {

    private final AutoService autoService;
    private final DtoMapper dtoMapper;

    public AutoController(AutoService autoService, DtoMapper dtoMapper) {
        this.autoService = autoService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<AutoDto> getAllAutos() {
        return autoService.getAllAutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoDto> getAutoById(@PathVariable Long id) {
        return autoService.getAutoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AutoDto> createAuto(@RequestBody AutoDto autoDto) {
        AutoDto created = autoService.saveAuto(dtoMapper.toAuto(autoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoDto> updateAuto(@PathVariable Long id, @RequestBody AutoDto autoDto) {
        return autoService.getAutoById(id)
                .map(existing -> {
                    autoDto.setId(id);
                    return ResponseEntity.ok(autoService.saveAuto(dtoMapper.toAuto(autoDto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable Long id) {
        if (autoService.getAutoById(id).isPresent()) {
            autoService.deleteAuto(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}