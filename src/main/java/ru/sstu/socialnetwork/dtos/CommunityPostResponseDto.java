package ru.sstu.socialnetwork.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.sstu.socialnetwork.entities.Community;
import ru.sstu.socialnetwork.entities.User;

import java.time.LocalDateTime;

public class CommunityPostResponseDto {

    private Long id;
    private String postText;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationTimeStamp;
    private User author;
    private Community community;
    private Boolean isAuthor;

    public CommunityPostResponseDto() {
    }

    public CommunityPostResponseDto(Long id, String postText, LocalDateTime creationTimeStamp, User author,
                                    Community community, Boolean isAuthor) {
        this.id = id;
        this.postText = postText;
        this.creationTimeStamp = creationTimeStamp;
        this.author = author;
        this.community = community;
        this.isAuthor = isAuthor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Boolean getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(Boolean isAuthor) {
        this.isAuthor = isAuthor;
    }

}
