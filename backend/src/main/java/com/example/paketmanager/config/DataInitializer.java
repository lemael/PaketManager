package com.example.paketmanager.config;

import com.example.paketmanager.model.*;
import com.example.paketmanager.repository.auth.UserRepository;
import com.example.paketmanager.repository.dashboard.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDemoUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            String email = "demo@paketmanager.de";

            if (userRepository.existsByEmail(email)) {
                return;
            }

            User demoUser = new User(
                    "demo",
                    email,
                    passwordEncoder.encode("Demo1234!")
            );

            userRepository.save(demoUser);

            System.out.println("Demo account created successfully.");
        };
    }

    @Bean
    CommandLineRunner initDemoData(
            KundeRepository kundeRepository,
            AutoRepository autoRepository,
            FahrerRepository fahrerRepository,
            TransportRepository transportRepository,
            ColiRepository coliRepository
    ) {
        return args -> {

            if (kundeRepository.count() > 0) {
                return;
            }

            // --- Kunden (clients qui expédient/reçoivent des colis) ---
            Kunde k1 = kundeRepository.save(Kunde.builder()
                    .name("TechShop Berlin")
                    .mainContact("Laura König")
                    .city("Berlin")
                    .contact("+49 30 1234567")
                    .monthlyVolume("120 Sendungen")
                    .build());

            Kunde k2 = kundeRepository.save(Kunde.builder()
                    .name("Blumen Schmidt")
                    .mainContact("Peter Schmidt")
                    .city("München")
                    .contact("+49 89 7654321")
                    .monthlyVolume("85 Sendungen")
                    .build());

            Kunde k3 = kundeRepository.save(Kunde.builder()
                    .name("Buchhandlung Weber")
                    .mainContact("Julia Weber")
                    .city("Hamburg")
                    .contact("+49 40 9876543")
                    .monthlyVolume("30 Sendungen")
                    .build());

            // --- Autos ---
            Auto a1 = autoRepository.save(Auto.builder()
                    .name("Sprinter 1")
                    .plateNumber("B-PM 1001")
                    .model("Mercedes Sprinter 316")
                    .maxCapacity("1200 kg / 10 m³")
                    .mileage("45200 km")
                    .tuvInspection("10/2026")
                    .status(Auto.AutoStatus.IM_DIENST)
                    .build());

            Auto a2 = autoRepository.save(Auto.builder()
                    .name("Crafter 2")
                    .plateNumber("B-PM 1002")
                    .model("VW Crafter 35")
                    .maxCapacity("1000 kg / 8 m³")
                    .mileage("32100 km")
                    .tuvInspection("05/2027")
                    .status(Auto.AutoStatus.VERFÜGBAR)
                    .build());

            Auto a3 = autoRepository.save(Auto.builder()
                    .name("Transit 3")
                    .plateNumber("B-PM 1003")
                    .model("Ford Transit 350")
                    .maxCapacity("1400 kg / 12 m³")
                    .mileage("67800 km")
                    .tuvInspection("02/2026")
                    .status(Auto.AutoStatus.INSPEKTION)
                    .build());

            // --- Fahrer ---
            Fahrer f1 = fahrerRepository.save(Fahrer.builder()
                    .name("Max Mustermann")
                    .licenseClass("C")
                    .phoneNumber("+49 170 1234567")
                    .status(Fahrer.FahrerStatus.IN_AUSLIEFERUNG)
                    .build());

            Fahrer f2 = fahrerRepository.save(Fahrer.builder()
                    .name("Anna Beispiel")
                    .licenseClass("B")
                    .phoneNumber("+49 170 7654321")
                    .status(Fahrer.FahrerStatus.VERFÜGBAR)
                    .build());

            Fahrer f3 = fahrerRepository.save(Fahrer.builder()
                    .name("Tom Test")
                    .licenseClass("CE")
                    .phoneNumber("+49 170 9876543")
                    .status(Fahrer.FahrerStatus.PAUSIERT)
                    .build());

            // --- Transporte ---
            Transport t1 = transportRepository.save(Transport.builder()
                    .transportNumber("TR-1001")
                    .zone("Berlin Nord")
                    .colisCount(3)
                    .deliveredCount(1)
                    .status(Transport.TransportStatus.IN_TRANSIT)
                    .fahrer(f1)
                    .auto(a1)
                    .build());

            Transport t2 = transportRepository.save(Transport.builder()
                    .transportNumber("TR-1002")
                    .zone("München Süd")
                    .colisCount(2)
                    .deliveredCount(2)
                    .status(Transport.TransportStatus.DELIVERED)
                    .fahrer(f2)
                    .auto(a2)
                    .build());

            // --- Colis ---
            coliRepository.saveAll(List.of(
                    Coli.builder()
                            .coliNumber("PKG-1001")
                            .recipient("Berlin (10115)")
                            .formatAndWeight("1.2 kg (S)")
                            .status(Coli.ColiStatus.IN_ARBEIT)
                            .kunde(k1)
                            .transport(t1)
                            .build(),
                    Coli.builder()
                            .coliNumber("PKG-1002")
                            .recipient("Berlin (10117)")
                            .formatAndWeight("3.5 kg (M)")
                            .status(Coli.ColiStatus.GELIEFERT)
                            .kunde(k1)
                            .transport(t1)
                            .build(),
                    Coli.builder()
                            .coliNumber("PKG-1003")
                            .recipient("Potsdam (14467)")
                            .formatAndWeight("0.8 kg (S)")
                            .status(Coli.ColiStatus.AUSSTEHEND)
                            .kunde(k2)
                            .build(),
                    Coli.builder()
                            .coliNumber("PKG-1004")
                            .recipient("München (80331)")
                            .formatAndWeight("5.0 kg (L)")
                            .status(Coli.ColiStatus.GELIEFERT)
                            .kunde(k2)
                            .transport(t2)
                            .build(),
                    Coli.builder()
                            .coliNumber("PKG-1005")
                            .recipient("Hamburg (20095)")
                            .formatAndWeight("2.1 kg (M)")
                            .status(Coli.ColiStatus.ANOMALIE)
                            .kunde(k3)
                            .build()
            ));

            System.out.println("Demo data created successfully.");
        };
    }
}