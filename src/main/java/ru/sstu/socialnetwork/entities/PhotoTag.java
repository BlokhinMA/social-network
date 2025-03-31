package ru.sstu.socialnetwork.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "photo_tags", uniqueConstraints = {@UniqueConstraint(columnNames = {"tag", "photo_id"})})
public class PhotoTag {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String tag;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Photo photo;

    public PhotoTag() {
    }

    public PhotoTag(Long id, String tag, Photo photo) {
        this.id = id;
        this.tag = tag;
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "PhotoTag{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", photo=" + photo +
                '}';
    }

}
