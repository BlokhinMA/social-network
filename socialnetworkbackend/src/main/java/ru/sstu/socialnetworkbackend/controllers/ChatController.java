package ru.sstu.socialnetworkbackend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.sstu.socialnetworkbackend.configs.SecurityUtil;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageDto;
import ru.sstu.socialnetworkbackend.entities.Message;
import ru.sstu.socialnetworkbackend.services.MessageService;

@Controller
public class ChatController {


    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService service;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService service) {
        this.messagingTemplate = messagingTemplate;
        this.service = service;
    }

    @MessageMapping("/app/sendMessage")
    @SendTo("/topic/messages")
    public void sendMessage(@Payload/*@Valid*/ MessageDto dto) {
        Message msg = service.create(dto);
        messagingTemplate.convertAndSendToUser(dto.toUserId().toString(), "/queue/messages", msg);
    }

}
