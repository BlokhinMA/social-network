package ru.sstu.socialnetworkbackend.dtos.communities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommunityDto(
    @NotBlank(message = "Поле \"Название\" не должно состоять только из пробелов")
    @Size(min = 1, max = 255, message = "Поле \"Название\" должно содержать минимум 1 и максимум 255 символов")
    String name
) {

}
