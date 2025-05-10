package ru.sstu.socialnetworkbackend.dtos.friends;

import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public record FriendsResponseDto(
    User user,
    List<User> friends
) {

}
