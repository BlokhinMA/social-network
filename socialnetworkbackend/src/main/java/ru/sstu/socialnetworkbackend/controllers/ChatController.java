package ru.sstu.socialnetworkbackend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageDto;
import ru.sstu.socialnetworkbackend.entities.Message;
import ru.sstu.socialnetworkbackend.services.MessageService;

@Controller
public class ChatController {

    private final MessageService service;

    public ChatController(MessageService service) {
        this.service = service;
    }

    @MessageMapping("app/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload/*@Valid*/ MessageDto dto) {
        return service.create(dto);
    }

}
