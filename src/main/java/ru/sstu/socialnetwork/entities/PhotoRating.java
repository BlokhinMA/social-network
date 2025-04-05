package ru.sstu.socialnetwork.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "photo_ratings", uniqueConstraints = {@UniqueConstraint(columnNames = {"rating_user_id", "photo_id"})})
public class PhotoRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Boolean rating;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User ratingUser;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Photo photo;

    public PhotoRating() {
    }

    public PhotoRating(Long id, Boolean rating, User ratingUser, Photo photo) {
        this.id = id;
        this.rating = rating;
        this.ratingUser = ratingUser;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

    public User getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(User ratingUser) {
        this.ratingUser = ratingUser;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "PhotoRating{" +
                "id=" + id +
                ", rating=" + rating +
                ", ratingUser=" + ratingUser +
                ", photo=" + photo +
                '}';
    }

}
