package ru.sstu.socialnetwork.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_posts")
public class CommunityPost {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String postText;
    @Column(nullable = false)
    private LocalDateTime creationTimeStamp = LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User author;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Community community;

    public CommunityPost() {
    }

    public CommunityPost(Long id, String postText, LocalDateTime creationTimeStamp, User author, Community community) {
        this.id = id;
        this.postText = postText;
        this.creationTimeStamp = creationTimeStamp;
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
