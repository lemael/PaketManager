package com.example.paketmanager.config;

import com.example.paketmanager.model.User;
import com.example.paketmanager.repository.auth.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}