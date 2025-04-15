package ru.sstu.socialnetwork.dtos;

public class FindPhotosDto {

    private String searchTerm;
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
