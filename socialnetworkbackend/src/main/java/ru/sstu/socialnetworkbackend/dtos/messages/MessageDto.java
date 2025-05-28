package ru.sstu.socialnetworkbackend.dtos.messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageDto(
    @NotNull(message = "Поле \"toUserId\" должно быть заполнено")
    Long toUserId,
    @NotBlank(message = "Поле \"Сообщение\" не должно состоять только из пробелов")
    @Size(min = 1, message = "Поле \"Сообщение\" должно содержать минимум 1 символ")
    String msg
) {

}
