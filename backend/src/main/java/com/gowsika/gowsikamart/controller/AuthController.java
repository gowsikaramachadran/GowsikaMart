package com.gowsika.gowsikamart.controller;

import com.gowsika.gowsikamart.dto.AuthResponse;
import com.gowsika.gowsikamart.dto.LoginRequest;
import com.gowsika.gowsikamart.dto.RegisterRequest;
import com.gowsika.gowsikamart.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // JWT is stateless - logout is handled client-side by discarding the token
        return ResponseEntity.ok("Logged out successfully");
    }
}
