package ru.sstu.socialnetworkbackend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User fromUser;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User toUser;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String msg;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime writingTimeStamp;

    public Message() {
    }

    public Message(User fromUser, User toUser, String msg) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
                ", msg='" + msg + '\'' +
                ", writingTimeStamp=" + writingTimeStamp +
                '}';
    }

}
