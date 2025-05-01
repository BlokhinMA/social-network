package ru.sstu.socialnetworkbackend.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sstu.socialnetworkbackend.dtos.AuthRequest;
import ru.sstu.socialnetworkbackend.dtos.AuthResponse;
import ru.sstu.socialnetworkbackend.services.AuthService;

@RestController()
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody @Valid AuthRequest authRequest, HttpServletResponse response) {

        AuthResponse authResponse = service.auth(authRequest);

        String token = authResponse.getToken();

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse)/*header(HttpHeaders.SET_COOKIE, service.auth(authRequest)).build()*/;
    }

}
