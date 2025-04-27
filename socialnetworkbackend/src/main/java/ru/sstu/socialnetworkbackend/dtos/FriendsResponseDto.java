package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public class FriendsResponseDto {

    private User user;
    private List<User> friends;

    public FriendsResponseDto() {
    }

    public FriendsResponseDto(User user, List<User> friends) {
        this.user = user;
        this.friends = friends;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

}
