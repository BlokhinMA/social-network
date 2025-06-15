package ru.sstu.socialnetworkbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/show_all")
    public ResponseEntity<List<MessageResponseDto>> showAll() {
        return ResponseEntity.ok(service.showAllCompanions());
    }

    @GetMapping("/show/{companionId}")
    public ResponseEntity<List<Message>> show(@PathVariable Long companionId) {
        return ResponseEntity.ok(service.showAllMessages(companionId));
    }

}
