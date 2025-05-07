package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MessageDto(
        Long fromUserId,
        Long toUserId,
        @NotBlank(message = "Поле \"Сообщение\" не должно состоять только из пробелов")
        @Size(min = 1, message = "Поле \"Сообщение\" должно содержать минимум 1 символ")
        String message
) {

}
