package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ChangeAlbumAccessTypeDto {

    @NotNull(message = "Поле \"Айди\" должно быть заполнено")
    private Long id;
    @NotNull(message = "Поле \"Тип доступа\" должно быть заполнено")
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
