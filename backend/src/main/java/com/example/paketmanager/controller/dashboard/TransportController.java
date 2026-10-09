package com.example.paketmanager.controller.dashboard;

import com.example.paketmanager.dto.dashboard.TransportDto;
import com.example.paketmanager.dto.dashboard.DtoMapper;
import com.example.paketmanager.model.Transport;
import com.example.paketmanager.service.dashboard.TransportService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class TransportController {

    private final TransportService transportService;
    private final DtoMapper dtoMapper;

    public TransportController(TransportService transportService, DtoMapper dtoMapper) {
        this.transportService = transportService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<TransportDto> getAllTransports() {
        return transportService.getAllTransports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportDto> getTransportById(@PathVariable Long id) {
        return transportService.getTransportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransportDto> createTransport(@RequestBody TransportDto transportDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transportService.saveTransport(dtoMapper.toTransport(transportDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportDto> updateTransport(@PathVariable Long id, @RequestBody TransportDto transportDto) {
        return transportService.getTransportById(id)
                .map(existing -> {
                    transportDto.setId(id);
                    return ResponseEntity.ok(transportService.saveTransport(dtoMapper.toTransport(transportDto)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        if (transportService.getTransportById(id).isPresent()) {
            transportService.deleteTransport(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}