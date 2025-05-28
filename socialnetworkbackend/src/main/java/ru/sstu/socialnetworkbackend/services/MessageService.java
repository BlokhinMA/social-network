package ru.sstu.socialnetworkbackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageDto;
import ru.sstu.socialnetworkbackend.dtos.messages.MessageResponseDto;
import ru.sstu.socialnetworkbackend.entities.Message;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.repositories.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository msgRepository;
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    public MessageService(MessageRepository msgRepository, UserService userService) {
        this.msgRepository = msgRepository;
        this.userService = userService;
    }

    public Message create(MessageDto dto) {
        User fromUser = userService.getCurrentUser();
        User toUser = userService.getUserById(dto.toUserId());
        Message msg = new Message(
                fromUser,
                toUser,
                dto.msg()
        );
        msg = msgRepository.save(msg);
        LOG.info("Было создано сообщение {}", msg);
        return msg;
    }

    public List<MessageResponseDto> showAllCompanions() {
        User currentUser = userService.getCurrentUser();
        List<User> companions = msgRepository.findAllCompanionsByUserId(currentUser.getId());
        List<MessageResponseDto> dtos = new ArrayList<>();
        User companion;
        for (User user : companions) {
            companion = user;
            dtos.add(new MessageResponseDto(
                    companion,
                    msgRepository.findLastByUserId(currentUser.getId(), companion.getId())
            ));
        }
        return dtos;
    }

    public List<Message> showAllMessages(Long companionId) {
        User currentUser = userService.getCurrentUser();
        return msgRepository.findAllByUserId(currentUser.getId(), companionId);
    }

}
