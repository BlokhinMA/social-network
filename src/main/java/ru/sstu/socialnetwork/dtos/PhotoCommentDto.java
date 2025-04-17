package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PhotoCommentDto {

    @NotBlank(message = "Поле \"Комментарий\" не должно состоять только из пробелов")
    @Size(min = 1, message = "Поле \"Комментарий\" должно содержать минимум 1 символ")
    private String comment;
    @NotNull(message = "Поле \"photoId\" не должно быть null")
    private Long photoId;

    public PhotoCommentDto() {
    }

    public PhotoCommentDto(String comment, Long photoId) {
        this.comment = comment;
        this.photoId = photoId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

}
