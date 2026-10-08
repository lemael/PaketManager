package com.example.paketmanager.service.auth;

import com.example.paketmanager.dto.auth.AuthResponse;
import com.example.paketmanager.dto.auth.LoginRequest;
import com.example.paketmanager.dto.auth.RegisterRequest;
import com.example.paketmanager.exception.InvalidCredentialsException;
import com.example.paketmanager.exception.UserAlreadyExistsException;
import com.example.paketmanager.model.User;
import com.example.paketmanager.repository.auth.UserRepository;
import com.example.paketmanager.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException(
                    "User already exists"
            );
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(
                    "User already exists"
            );
        }

        String hashedPassword =
                passwordEncoder.encode(request.password());

        User user = new User(
                request.username(),
                request.email(),
                hashedPassword
        );

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid username or password"
                        )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }

        String token =
                jwtService.generateToken(user.getUsername());

        return new AuthResponse(token);
    }
}