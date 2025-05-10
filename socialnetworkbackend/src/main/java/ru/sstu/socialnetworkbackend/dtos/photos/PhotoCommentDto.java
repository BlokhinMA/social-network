package ru.sstu.socialnetworkbackend.dtos.photos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PhotoCommentDto(
    @NotBlank(message = "Поле \"Комментарий\" не должно состоять только из пробелов")
    @Size(min = 1, message = "Поле \"Комментарий\" должно содержать минимум 1 символ")
    String comment,
    @NotNull(message = "Поле \"photoId\" должно быть заполнено")
    Long photoId
) {

}
