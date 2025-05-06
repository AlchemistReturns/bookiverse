package com.abrar.bookiverse.service;

import com.abrar.bookiverse.dto.AuthRequest;
import com.abrar.bookiverse.dto.AuthResponse;
import com.abrar.bookiverse.entity.Role;
import com.abrar.bookiverse.entity.User;
import com.abrar.bookiverse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .authProvider("LOCAL")
                .build();
        System.out.println("Registering user");
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        System.out.println("Logging in user");

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}
