package com.example.paketmanager.repository.dashboard;

import com.example.paketmanager.model.Kunde;
import com.example.paketmanager.model.Coli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface KundeRepository extends JpaRepository<Kunde, Long> {
   // List<Coli> findByKundeId(Long id);
}