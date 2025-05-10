package ru.sstu.socialnetworkbackend.dtos.albums;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangeAlbumAccessTypeDto(
    @NotNull(message = "Поле \"Айди\" должно быть заполнено")
    Long id,
    @NotNull(message = "Поле \"Тип доступа\" должно быть заполнено")
    @Pattern(regexp = "ALL|FRIENDS", message = "Значение должно быть равным \"ALL\" или \"FRIENDS\"")
    String accessType
) {

}
