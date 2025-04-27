package ru.sstu.socialnetworkbackend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originalFileName;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    @JsonIgnore
    private byte[] bytes;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationTimeStamp;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Album album;

    @PrePersist
    private void onCreate() {
        if (creationTimeStamp == null) {
            creationTimeStamp = LocalDateTime.now();
        }
    }

    public Photo() {
    }

    public Photo(Long id, String originalFileName, Long size, String contentType, byte[] bytes,
                 LocalDateTime creationTimeStamp, Album album) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
        this.bytes = bytes;
        this.creationTimeStamp = creationTimeStamp;
        this.album = album;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", originalFileName='" + originalFileName + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", creationTimeStamp=" + creationTimeStamp +
                ", album=" + album +
                '}';
    }

}
