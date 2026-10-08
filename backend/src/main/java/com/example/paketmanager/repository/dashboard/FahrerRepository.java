package com.example.paketmanager.repository.dashboard;

import com.example.paketmanager.model.Fahrer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FahrerRepository extends JpaRepository<Fahrer, Long> {
    List<Fahrer> findByStatus(Fahrer.FahrerStatus status);
}