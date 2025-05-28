package ru.sstu.socialnetworkbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "photo_tags", uniqueConstraints = {@UniqueConstraint(columnNames = {"tag", "photo_id"})})
public class PhotoTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String tag;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Photo photo;

    public PhotoTag() {
    }

    public PhotoTag(String tag, Photo photo) {
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
