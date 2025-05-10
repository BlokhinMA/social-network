package ru.sstu.socialnetworkbackend.dtos.photos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PhotoTagDto(
    @NotNull(message = "Поле \"Тег\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Тег\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Тег\" не должно содержать пробелы")
    String tag,
    @NotNull(message = "Поле \"PhotoId\" должно быть заполнено")
    Long photoId
) {

}
