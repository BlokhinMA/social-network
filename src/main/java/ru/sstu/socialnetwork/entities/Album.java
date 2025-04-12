package ru.sstu.socialnetwork.entities;

import jakarta.persistence.*;
import ru.sstu.socialnetwork.entities.enums.AccessType;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessType accessType;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User owner;

    public Album() {
    }

    public Album(Long id, String title, AccessType accessType, User owner) {
        this.id = id;
        this.title = title;
        this.accessType = accessType;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() { // todo переписать везде метод toString
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", accessType='" + accessType + '\'' +
                ", owner=" + owner +
                '}';
    }

}
