package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sstu.socialnetworkbackend.dtos.auth.AuthRequest;
import ru.sstu.socialnetworkbackend.dtos.auth.AuthResponse;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.services.AuthService;

@RestController()
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<User> auth(@RequestBody @Valid AuthRequest authRequest) {

        AuthResponse response = service.auth(authRequest);

        ResponseCookie cookie = ResponseCookie.from("jwt", response.token())
            .httpOnly(true)
            .secure(true)
            .sameSite("strict")
            .path("/")
            .maxAge(24 * 60 * 60)
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response.user());
    }

}
