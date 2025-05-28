package ru.sstu.socialnetworkbackend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "photo_comments")
public class PhotoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime commentingTimeStamp;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User commentingUser;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Photo photo;

    public PhotoComment() {
    }

    public PhotoComment(String comment, User commentingUser, Photo photo) {
        this.comment = comment;
        this.commentingUser = commentingUser;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreationTimeStamp() {
        return commentingTimeStamp;
    }

    public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
        this.commentingTimeStamp = creationTimeStamp;
    }

    public User getCommentingUser() {
        return commentingUser;
    }

    public void setCommentingUser(User commentingUser) {
        this.commentingUser = commentingUser;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @PrePersist
    private void onCreate() {
        if (commentingTimeStamp == null) {
            commentingTimeStamp = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "PhotoComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", commentingTimeStamp=" + commentingTimeStamp +
                ", commentingUser=" + commentingUser +
                ", photo=" + photo +
                '}';
    }

}
