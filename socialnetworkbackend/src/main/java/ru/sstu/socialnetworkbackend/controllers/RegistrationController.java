package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.users.UserDto;
import ru.sstu.socialnetworkbackend.services.RegistrationService;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(service.register(userDto));
    }

    @PatchMapping("/confirm/{token}")
    public ResponseEntity<?> confirm(@PathVariable String token) {
        return ResponseEntity.ok(service.confirm(token));
    }

}
