package ru.sstu.socialnetworkbackend.dtos.albums;

import ru.sstu.socialnetworkbackend.entities.Album;

import java.util.List;

public record AlbumResponseDto(
    Album album,
    List<Long> photos,
    Boolean isOwner
) {

}
