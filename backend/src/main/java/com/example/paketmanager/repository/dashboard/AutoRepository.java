package com.example.paketmanager.repository.dashboard;

import com.example.paketmanager.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
    List<Auto> findByStatus(Auto.AutoStatus status);
    Auto findByPlateNumber(String plateNumber);
}