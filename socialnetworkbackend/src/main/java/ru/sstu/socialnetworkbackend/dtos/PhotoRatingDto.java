package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotNull;

public class PhotoRatingDto {

    @NotNull(message = "Поле \"rating\" должно быть заполнено")
    private Boolean rating;
    @NotNull(message = "Поле \"photoId\" должно быть заполнено")
    private Long photoId;

    public PhotoRatingDto() {
    }

    public PhotoRatingDto(Boolean rating, Long photoId) {
        this.rating = rating;
        this.photoId = photoId;
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

}
