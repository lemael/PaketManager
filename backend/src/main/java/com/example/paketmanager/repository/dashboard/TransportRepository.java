package com.example.paketmanager.repository.dashboard;

import com.example.paketmanager.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
    Optional<Transport> findByTransportNumber(String transportNumber);
    List<Transport> findByStatus(Transport.TransportStatus status);
    List<Transport> findByFahrerId(Long id);
    List<Transport> findByAutoId(Long id);
}