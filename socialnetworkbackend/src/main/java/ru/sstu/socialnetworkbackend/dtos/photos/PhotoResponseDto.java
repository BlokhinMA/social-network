package ru.sstu.socialnetworkbackend.dtos.photos;

import ru.sstu.socialnetworkbackend.entities.Photo;
import ru.sstu.socialnetworkbackend.entities.PhotoTag;

import java.util.List;

public record PhotoResponseDto(
    Photo photo,
    List<PhotoTag> tags,
    List<PhotoCommentResponseDto> comments,
    Double rating,
    Boolean userRating,
    Boolean isOwner
) {

}
