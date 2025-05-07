package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public record FriendsResponseDto(
        User user,
        List<User> friends
) {

}
