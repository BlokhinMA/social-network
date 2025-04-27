package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sstu.socialnetworkbackend.dtos.UserDto;
import ru.sstu.socialnetworkbackend.services.RegistrationService;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(service.register(userDto));
    }



}
