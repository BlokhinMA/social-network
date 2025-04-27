package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sstu.socialnetworkbackend.dtos.AuthRequest;
import ru.sstu.socialnetworkbackend.services.AuthService;

@RestController()
@RequestMapping("/api")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, service.auth(authRequest)).build();
    }

}
