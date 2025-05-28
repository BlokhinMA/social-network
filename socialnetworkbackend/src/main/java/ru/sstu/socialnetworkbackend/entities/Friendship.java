package ru.sstu.socialnetworkbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "friendships", uniqueConstraints = {@UniqueConstraint(columnNames = {"first_user_id", "second_user_id"})})
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User firstUser;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User secondUser;
    @Column(nullable = false)
    private Boolean accepted;

    public Friendship() {
    }

    public Friendship(User firstUser, User secondUser, Boolean accepted) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.accepted = accepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                ", accepted=" + accepted +
                '}';
    }

}
