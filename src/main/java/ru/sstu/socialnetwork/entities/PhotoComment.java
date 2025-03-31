package ru.sstu.socialnetwork.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "photo_comments")
public class PhotoComment {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private LocalDateTime commentingTimeStamp = LocalDateTime.now();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User commentingUser;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Photo photo;

    public PhotoComment() {
    }

    public PhotoComment(Long id, String comment, LocalDateTime commentingTimeStamp, User commentingUser, Photo photo) {
        this.id = id;
        this.comment = comment;
        this.commentingTimeStamp = commentingTimeStamp;
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

    public LocalDateTime getCommentingTimeStamp() {
        return commentingTimeStamp;
    }

    public void setCommentingTimeStamp(LocalDateTime commentingTimeStamp) {
        this.commentingTimeStamp = commentingTimeStamp;
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
