package ru.sstu.socialnetworkbackend.services;

import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.repositories.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


}
