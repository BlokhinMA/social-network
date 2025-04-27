package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.PhotoRating;

public class PhotoRatingResponseDto {

    private PhotoRating photoRating;
    private Double rating;

    public PhotoRatingResponseDto() {
    }

    public PhotoRatingResponseDto(PhotoRating photoRating, Double rating) {
        this.photoRating = photoRating;
        this.rating = rating;
    }

    public PhotoRating getPhotoRating() {
        return photoRating;
    }

    public void setPhotoRating(PhotoRating photoRating) {
        this.photoRating = photoRating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
