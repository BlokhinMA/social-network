package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AlbumDto {

    @Size(min = 1, max = 255, message = "Поле \"Название\" должно содержать минимум 1 и максимум 255 символов")
    @NotBlank(message = "Поле \"Название\" не должно состоять только из пробелов")
    private String title;
    @NotNull(message = "Поле \"Тип доступа\" не должно быть null")
    @Pattern(regexp = "all|friends", message = "Значение должно быть равным \"all\" или \"friends\"")
    private String accessType;
    private Long userId;

    public AlbumDto() {
    }

    public AlbumDto(String title, String accessType, Long userId) {
        this.title = title;
        this.accessType = accessType;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
