package ru.sstu.socialnetwork.dtos;

public class PhotoRatingDto {

    private Boolean rating;
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
