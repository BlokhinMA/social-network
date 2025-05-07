package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.PhotoRating;

public record PhotoRatingResponseDto(
        PhotoRating photoRating,
        Double rating
) {

}
