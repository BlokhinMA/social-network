package ru.sstu.socialnetworkbackend.dtos.auth;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
    @NotNull(message = "Поле \"Логин\" должно быть заполнено")
    String username,
    @NotNull(message = "Поле \"Пароль\" должно быть заполнено")
    String password
) {

}
