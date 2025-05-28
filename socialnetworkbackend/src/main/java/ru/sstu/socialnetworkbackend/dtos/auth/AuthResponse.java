package ru.sstu.socialnetworkbackend.dtos.auth;

import ru.sstu.socialnetworkbackend.entities.User;

public record AuthResponse(
    User user,
    String token
) {

}
