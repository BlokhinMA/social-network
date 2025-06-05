package ru.sstu.socialnetworkbackend.dtos.photos;

import jakarta.validation.constraints.*;

public record FindPhotosDto(
    @NotNull(message = "Поле \"Условие поиска\" должно быть заполнено")
    @Pattern(regexp = "creationTimeStamp|tag|comment", message = "Значение должно быть равным \"creationTimeStamp\", \"tag\" или \"comment\"")
    String searchTerm,
    @Size(min = 1, message = "Поле \"Ключевое слово\" должно содержать минимум 1 символ")
    @NotBlank(message = "Поле \"Ключевое слово\" не должно состоять только из пробелов")
    String keyword
) {

}
