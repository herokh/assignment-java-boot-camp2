package com.javabootcamp.assessment2.features.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/signin")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse signIn(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.signIn(authRequest);
        return authResponse;
    }
}
