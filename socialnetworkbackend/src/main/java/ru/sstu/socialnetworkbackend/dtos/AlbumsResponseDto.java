package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.Album;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public class AlbumsResponseDto {

    private User owner;
    private List<Album> albums;

    public AlbumsResponseDto() {
    }

    public AlbumsResponseDto(User owner, List<Album> albums) {
        this.owner = owner;
        this.albums = albums;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

}
