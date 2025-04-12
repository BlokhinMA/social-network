package ru.sstu.socialnetwork.pagecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    @GetMapping
    public String index(Principal principal) {
        return redirectToMyProfile(principal, "index");
    }

    @GetMapping("/sign_up")
    public String signUp(Principal principal) {
        return redirectToMyProfile(principal, "sign_up");
    }

    @GetMapping("/sign_in")
    public String signIn(Principal principal) {
        return redirectToMyProfile(principal, "sign_in");
    }

    @GetMapping("/my_profile")
    public String myProfile(Principal principal) {
        return redirectToSignIn(principal, "my_profile");
    }

    @GetMapping("/my_friends")
    public String myFriends(Principal principal) {
        return redirectToSignIn(principal, "my_friends");
    }

    @GetMapping("/my_albums")
    public String myAlbums(Principal principal) {
        return redirectToSignIn(principal, "my_albums");
    }

    @GetMapping("/my_communities")
    public String myCommunities(Principal principal) {
        return redirectToSignIn(principal, "my_communities");
    }

    @GetMapping("/my_messages")
    public String myMessages(Principal principal) {
        return redirectToSignIn(principal, "my_messages");
    }

    @GetMapping("/community_management")
    public String communityManagement(Principal principal) {
        return redirectToSignIn(principal, "community_management");
    }

    @GetMapping("/friend_requests")
    public String friendRequests(Principal principal) {
        return redirectToSignIn(principal, "friend_requests");
    }

    @GetMapping("/album/*")
    public String album(Principal principal) {
        return redirectToSignIn(principal, "album");
    }

    @GetMapping("/albums/*")
    public String albums(Principal principal) {
        return redirectToSignIn(principal, "albums");
    }

    @GetMapping("/communities/*")
    public String communities(Principal principal) {
        return redirectToSignIn(principal, "communities");
    }

    @GetMapping("/community/*")
    public String community(Principal principal) {
        return redirectToSignIn(principal, "community");
    }

    @GetMapping("/find_albums")
    public String findAlbums(Principal principal) {
        return redirectToSignIn(principal, "find_albums");
    }

    @GetMapping("/find_communities")
    public String findCommunities(Principal principal) {
        return redirectToSignIn(principal, "find_communities");
    }

    @GetMapping("/find_friends")
    public String findFriends(Principal principal) {
        return redirectToSignIn(principal, "find_friends");
    }

    @GetMapping("/find_photos")
    public String findPhotos(Principal principal) {
        return redirectToSignIn(principal, "find_photos");
    }

    @GetMapping("/friends/*")
    public String friends(Principal principal) {
        return redirectToSignIn(principal, "friends");
    }

    @GetMapping("/message/*")
    public String message(Principal principal) {
        return redirectToSignIn(principal, "message");
    }

    @GetMapping("/photo/*")
    public String photo(Principal principal) {
        return redirectToSignIn(principal, "photo");
    }

    @GetMapping("/profile/*")
    public String profile(Principal principal) {
        return redirectToSignIn(principal, "profile");
    }

    private String redirectToMyProfile(Principal principal, String page) {
        if (principal != null)
            return "redirect:/my_profile";
        return page;
    }

    private String redirectToSignIn(Principal principal, String page) {
        if (principal == null)
            return "redirect:/sign_in";
        return page;
    }

}
