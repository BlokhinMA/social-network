package ru.sstu.socialnetwork.dtos;

public class PhotoRatingDto {

    private Boolean rating;
    private String ratingUserLogin;
    private Long photo;

    public PhotoRatingDto() {
    }

    public PhotoRatingDto(Boolean rating, String ratingUserLogin, Long photo) {
        this.rating = rating;
        this.ratingUserLogin = ratingUserLogin;
        this.photo = photo;
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

    public String getRatingUserLogin() {
        return ratingUserLogin;
    }

    public void setRatingUserLogin(String ratingUserLogin) {
        this.ratingUserLogin = ratingUserLogin;
    }

    public Long getPhoto() {
        return photo;
    }

    public void setPhoto(Long photo) {
        this.photo = photo;
    }

}
