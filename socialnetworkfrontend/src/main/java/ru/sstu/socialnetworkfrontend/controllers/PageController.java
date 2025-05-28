package ru.sstu.socialnetworkfrontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

    @GetMapping("/confirm_registration/*")
    public String confirmRegistration() {
        return "confirm_registration";
    }

    @GetMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    @GetMapping("/my_profile")
    public String myProfile() {
        return "my_profile";
    }

    @GetMapping("/my_friends")
    public String myFriends() {
        return "my_friends";
    }

    @GetMapping("/my_albums")
    public String myAlbums() {
        return "my_albums";
    }

    @GetMapping("/my_communities")
    public String myCommunities() {
        return "my_communities";
    }

    @GetMapping("/my_messages")
    public String myMessages() {
        return "my_messages";
    }

    @GetMapping("/community_management")
    public String communityManagement() {
        return "community_management";
    }

    @GetMapping("/friend_requests")
    public String friendRequests() {
        return "friend_requests";
    }

    @GetMapping("/album/*")
    public String album() {
        return "album";
    }

    @GetMapping("/albums/*")
    public String albums() {
        return "albums";
    }

    @GetMapping("/communities/*")
    public String communities() {
        return "communities";
    }

    @GetMapping("/community/*")
    public String community() {
        return "community";
    }

    @GetMapping("/find_albums")
    public String findAlbums() {
        return "find_albums";
    }

    @GetMapping("/find_communities")
    public String findCommunities() {
        return "find_communities";
    }

    @GetMapping("/find_friends")
    public String findFriends() {
        return "find_friends";
    }

    @GetMapping("/find_photos")
    public String findPhotos() {
        return "find_photos";
    }

    @GetMapping("/friends/*")
    public String friends() {
        return "friends";
    }

    @GetMapping("/messages/*")
    public String message() {
        return "messages";
    }

    @GetMapping("/photo/*")
    public String photo() {
        return "photo";
    }

    @GetMapping("/profile/*")
    public String profile() {
        return "profile";
    }

}
