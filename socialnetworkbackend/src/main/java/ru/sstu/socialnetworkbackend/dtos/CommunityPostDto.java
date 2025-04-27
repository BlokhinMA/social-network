package ru.sstu.socialnetworkbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommunityPostDto {

    @NotBlank(message = "Поле \"Текст поста\" не должно состоять только из пробелов")
    @Size(min = 1, message = "Поле \"Текст поста\" должно содержать минимум 1 символ")
    private String postText;
    @NotNull(message = "Поле \"communityId\" не должно быть null")
    private Long communityId;

    public CommunityPostDto() {
    }

    public CommunityPostDto(String postText, Long communityId) {
        this.postText = postText;
        this.communityId = communityId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

}
