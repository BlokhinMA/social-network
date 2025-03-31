package ru.sstu.socialnetwork.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "friendships", uniqueConstraints = {@UniqueConstraint(columnNames = {"first_user_id", "second_user_id"})})
public class Friendship {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User firstUser;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User secondUser;
    @Column(nullable = false)
    private Boolean accepted;

    public Friendship() {
    }

    public Friendship(Long id, User firstUser, User secondUser, Boolean accepted) {
        this.id = id;
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
