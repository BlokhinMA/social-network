package ru.sstu.socialnetwork.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User fromUser;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User toUser;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime writingTimeStamp;

    public Message() {
    }

    public Message(User fromUser, User toUser, String message, LocalDateTime writingTimeStamp) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
        this.writingTimeStamp = writingTimeStamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getWritingTimeStamp() {
        return writingTimeStamp;
    }

    public void setWritingTimeStamp(LocalDateTime writingTimeStamp) {
        this.writingTimeStamp = writingTimeStamp;
    }

    @PrePersist
    private void onCreate() {
        if (writingTimeStamp == null) {
            writingTimeStamp = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", message='" + message + '\'' +
                ", writingTimeStamp=" + writingTimeStamp +
                '}';
    }

}
