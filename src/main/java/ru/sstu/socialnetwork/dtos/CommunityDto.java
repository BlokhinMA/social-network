package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommunityDto {

    @NotBlank(message = "Поле \"Название\" не должно состоять только из пробелов")
    @Size(min = 1, max = 255, message = "Поле \"Название\" должно содержать минимум 1 и максимум 255 символов")
    private String name;
    private Long creatorId;

    public CommunityDto() {
    }

    public CommunityDto(String name, Long creatorId) {
        this.name = name;
        this.creatorId = creatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

}
