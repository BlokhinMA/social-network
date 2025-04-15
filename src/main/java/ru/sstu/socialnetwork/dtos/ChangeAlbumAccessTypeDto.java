package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ChangeAlbumAccessTypeDto {

    private Long id;
    @NotNull(message = "Поле \"Тип доступа\" не должно быть null")
    @Pattern(regexp = "ALL|FRIENDS", message = "Значение должно быть равным \"ALL\" или \"FRIENDS\"")
    private String accessType;

    public ChangeAlbumAccessTypeDto() {
    }

    public ChangeAlbumAccessTypeDto(Long id, String accessType) {
        this.id = id;
        this.accessType = accessType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

}
