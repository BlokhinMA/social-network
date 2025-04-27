package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.Photo;
import ru.sstu.socialnetworkbackend.entities.PhotoTag;

import java.util.List;

public class PhotoResponseDto {

    private Photo photo;
    private List<PhotoTag> tags;
    private List<PhotoCommentResponseDto> comments;
    private Double rating;
    private Boolean userRating;
    private Boolean isOwner;

    public PhotoResponseDto() {
    }

    public PhotoResponseDto(Photo photo, List<PhotoTag> tags, List<PhotoCommentResponseDto> comments, Double rating,
                            Boolean userRating, Boolean isOwner) {
        this.photo = photo;
        this.tags = tags;
        this.comments = comments;
        this.rating = rating;
        this.userRating = userRating;
        this.isOwner = isOwner;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<PhotoTag> getTags() {
        return tags;
    }

    public void setTags(List<PhotoTag> tags) {
        this.tags = tags;
    }

    public List<PhotoCommentResponseDto> getComments() {
        return comments;
    }

    public void setComments(List<PhotoCommentResponseDto> comments) {
        this.comments = comments;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getUserRating() {
        return userRating;
    }

    public void setUserRating(Boolean userRating) {
        this.userRating = userRating;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
    
}
