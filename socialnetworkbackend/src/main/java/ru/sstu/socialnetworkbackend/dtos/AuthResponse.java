package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.User;

public record AuthResponse(
        User user,
        String token
) {

}
