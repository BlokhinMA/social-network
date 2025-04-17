package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PhotoRatingDto {

    @Pattern(regexp = "true|false", message = "Значение рейтинга должно быть true или false")
    private Boolean rating;
    @NotNull(message = "Поле \"photoId\" не должно быть null")
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
