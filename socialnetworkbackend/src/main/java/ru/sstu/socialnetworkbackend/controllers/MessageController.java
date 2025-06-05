package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageDto;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageResponseDto;
import ru.sstu.socialnetworkbackend.entities.Message;
import ru.sstu.socialnetworkbackend.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Message> create(@RequestBody @Valid MessageDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/show_all")
    public ResponseEntity<List<MessageResponseDto>> showAll() {
        return ResponseEntity.ok(service.showAllCompanions());
    }

    @GetMapping("/show/{companionId}")
    public ResponseEntity<List<Message>> show(@PathVariable Long companionId) {
        return ResponseEntity.ok(service.showAllMessages(companionId));
    }

}
