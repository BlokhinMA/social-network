package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageDto;
import ru.sstu.socialnetworkbackend.services.MessageService;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid MessageDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/show_all")
    public ResponseEntity<?> showAll() {
        return ResponseEntity.ok(service.showAllCompanions());
    }

    @GetMapping("/show/{companionId}")
    public ResponseEntity<?> show(@PathVariable Long companionId) {
        return ResponseEntity.ok(service.showAllMessages(companionId));
    }

}
