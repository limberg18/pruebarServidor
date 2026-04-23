package com.taller.controller;


import com.taller.dto.LoginRequestDTO;
import com.taller.dto.LoginResponseDTO;
import com.taller.service.Impl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(authService.login(dto));
    }
}
