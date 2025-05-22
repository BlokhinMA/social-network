package ru.sstu.socialnetworkbackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageDto;
import ru.sstu.socialnetworkbackend.entities.Message;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.repositories.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public Message create(MessageDto dto) {
        User fromUser = userService.getCurrentUser();
        User toUser = userService.getUserById(dto.toUserId());
        Message message = new Message(
                fromUser,
                toUser,
                dto.message()
        );
        message = messageRepository.save(message);
        LOG.info("Было созданое сообщение {}", message);
        return message;
    }

}
