package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommunityDto {

    @NotNull(message = "Поле \"Название\" не должно быть null")
    @NotBlank(message = "Поле \"Название\" не должно состоять только из пробелов")
    @Size(min = 1, max = 255, message = "Поле \"Название\" должно содержать минимум 1 и максимум 255 символов")
    private String name; // todo разобраться с ошибками валидации

    public CommunityDto() {
    }

    public CommunityDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
