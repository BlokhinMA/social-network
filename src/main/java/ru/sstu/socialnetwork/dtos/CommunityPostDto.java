package ru.sstu.socialnetwork.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommunityPostDto {

    @NotBlank(message = "Поле \"Текст поста\" не должно состоять только из пробелов")
    @Size(min = 1, message = "Поле \"Текст поста\" должно содержать минимум 1 символ")
    private String postText;
    private Long authorId;
    private Long communityId;

    public CommunityPostDto() {
    }

    public CommunityPostDto(String postText, Long authorId, Long communityId) {
        this.postText = postText;
        this.authorId = authorId;
        this.communityId = communityId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

}
