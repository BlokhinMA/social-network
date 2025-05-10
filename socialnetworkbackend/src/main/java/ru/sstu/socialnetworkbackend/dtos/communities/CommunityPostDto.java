package ru.sstu.socialnetworkbackend.dtos.communities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommunityPostDto(
    @NotBlank(message = "Поле \"Текст поста\" не должно состоять только из пробелов")
    @Size(min = 1, message = "Поле \"Текст поста\" должно содержать минимум 1 символ")
    String postText,
    @NotNull(message = "Поле \"communityId\" должно быть заполнено")
    Long communityId
) {

}
