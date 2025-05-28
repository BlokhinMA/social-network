package ru.sstu.socialnetworkbackend.dtos.photos;

import ru.sstu.socialnetworkbackend.entities.PhotoComment;

public record PhotoCommentResponseDto(
    PhotoComment comment,
    Boolean isCommentingUser
) {

}
