package ru.sstu.socialnetworkbackend.dtos;

public class UserRatingResponseDto {

    private Boolean userRating;

    public UserRatingResponseDto() {
    }

    public UserRatingResponseDto(Boolean userRating) {
        this.userRating = userRating;
    }

    public Boolean getUserRating() {
        return userRating;
    }

    public void setUserRating(Boolean userRating) {
        this.userRating = userRating;
    }

}
