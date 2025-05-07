package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.Album;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public record AlbumsResponseDto(
        User owner,
        List<Album> albums
) {

}
