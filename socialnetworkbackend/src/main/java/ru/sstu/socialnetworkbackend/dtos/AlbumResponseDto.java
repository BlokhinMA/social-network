package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.Album;

import java.util.List;

public class AlbumResponseDto {

    private Album album;
    private List<Long> photos;
    private Boolean isOwner;

    public AlbumResponseDto() {
    }

    public AlbumResponseDto(Album album, List<Long> photos, Boolean isOwner) {
        this.album = album;
        this.photos = photos;
        this.isOwner = isOwner;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Long> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Long> photos) {
        this.photos = photos;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

}
