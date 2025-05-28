package ru.sstu.socialnetworkbackend.dtos.messages;

import ru.sstu.socialnetworkbackend.entities.Message;
import ru.sstu.socialnetworkbackend.entities.User;

public record MessageResponseDto(
    User companion,
    Message lastMsg
) {

}
