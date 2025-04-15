package ru.sstu.socialnetwork.services;

import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.repositories.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }



}
