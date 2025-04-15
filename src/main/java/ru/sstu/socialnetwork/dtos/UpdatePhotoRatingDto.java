package ru.sstu.socialnetwork.dtos;

public class UpdatePhotoRatingDto {

    private Long id;
    private Boolean rating;

    public UpdatePhotoRatingDto() {
    }

    public UpdatePhotoRatingDto(Long id, Boolean rating) {
        this.id = id;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

}
