package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.PhotoComment;

public record PhotoCommentResponseDto(
        PhotoComment comment,
        Boolean isCommentingUser
) {

}
