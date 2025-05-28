package ru.sstu.socialnetworkbackend.dtos.albums;

import ru.sstu.socialnetworkbackend.entities.Album;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public record AlbumsResponseDto(
    User owner,
    List<Album> albums
) {

}
