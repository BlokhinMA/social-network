package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.PhotoComment;

public class PhotoCommentResponseDto {

    private PhotoComment comment;
    private Boolean isCommentingUser;

    public PhotoCommentResponseDto() {
    }

    public PhotoCommentResponseDto(PhotoComment comment, Boolean isCommentingUser) {
        this.comment = comment;
        this.isCommentingUser = isCommentingUser;
    }

    public PhotoComment getComment() {
        return comment;
    }

    public void setComment(PhotoComment comment) {
        this.comment = comment;
    }

    public Boolean getCommentingUser() {
        return isCommentingUser;
    }

    public void setCommentingUser(Boolean commentingUser) {
        isCommentingUser = commentingUser;
    }

}
