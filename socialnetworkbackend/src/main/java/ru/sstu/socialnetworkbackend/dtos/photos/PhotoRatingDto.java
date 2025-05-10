package ru.sstu.socialnetworkbackend.dtos.photos;

import jakarta.validation.constraints.NotNull;

public record PhotoRatingDto(
    @NotNull(message = "Поле \"rating\" должно быть заполнено")
    Boolean rating,
    @NotNull(message = "Поле \"photoId\" должно быть заполнено")
    Long photoId
) {

}
