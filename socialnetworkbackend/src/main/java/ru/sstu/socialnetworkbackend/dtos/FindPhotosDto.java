package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.*;

public class FindPhotosDto {

    @NotNull(message = "Поле \"Условие поиска\" должно быть заполнено")
    @Pattern(regexp = "creationTimeStamp|tag|comment", message = "Значение должно быть равным \"creationTimeStamp\", \"tag\" или \"comment\"")
    private String searchTerm;
    @Min(value = 1, message = "Поле \"Ключевое слово\" должно содержать минимум 1 символ")
    @NotBlank(message = "Поле \"Ключевое слово\" не должно состоять только из пробелов")
    private String keyword;

    public FindPhotosDto() {
    }

    public FindPhotosDto(String searchTerm, String keyword) {
        this.searchTerm = searchTerm;
        this.keyword = keyword;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
