package ru.sstu.socialnetworkbackend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_posts")
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String postText;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationTimeStamp;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Community community;

    public CommunityPost() {
    }

    public CommunityPost(String postText, User author, Community community) {
        this.postText = postText;
        this.author = author;
        this.community = community;
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

    @PrePersist
    private void onCreate() {
        if (creationTimeStamp == null) {
            creationTimeStamp = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "CommunityPost{" +
                "id=" + id +
                ", postText='" + postText + '\'' +
                ", creationTimeStamp=" + creationTimeStamp +
                ", author=" + author +
                ", community=" + community +
                '}';
    }

}
