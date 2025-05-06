package com.abrar.bookiverse.controller;

import com.abrar.bookiverse.dto.AuthRequest;
import com.abrar.bookiverse.dto.AuthResponse;
import com.abrar.bookiverse.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest authRequest) {
        return authService.register(authRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }
}
