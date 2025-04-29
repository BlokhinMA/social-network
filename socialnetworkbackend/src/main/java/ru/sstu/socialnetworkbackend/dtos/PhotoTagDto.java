package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PhotoTagDto {

    @NotNull(message = "Поле \"Тег\" должно быть заполнено")
    @Size(min = 1, max = 255, message = "Поле \"Тег\" должно содержать минимум 1 и максимум 255 символов")
    @Pattern(regexp = "\\S+", message = "Поле \"Тег\" не должно содержать пробелы")
    private String tag;
    @NotNull(message = "Поле \"PhotoId\" должно быть заполнено")
    private Long photoId;

    public PhotoTagDto() {
    }

    public PhotoTagDto(String tag, Long photoId) {
        this.tag = tag;
        this.photoId = photoId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

}
