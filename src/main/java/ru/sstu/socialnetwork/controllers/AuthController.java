package ru.sstu.socialnetwork.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sstu.socialnetwork.dtos.AuthRequest;
import ru.sstu.socialnetwork.dtos.UserDto;
import ru.sstu.socialnetwork.services.AuthService;

@RestController()
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.register(userDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authService.auth(authRequest)).build();
    }

}
