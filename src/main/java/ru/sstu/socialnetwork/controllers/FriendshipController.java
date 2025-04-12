package ru.sstu.socialnetwork.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetwork.services.FriendshipService;

import java.security.Principal;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("/find/{word}")
    public ResponseEntity<?> find(@PathVariable String word, Principal principal) {
        return ResponseEntity.ok(friendshipService.find(word, principal));
    }

    @PostMapping("/create/{friendId}")
    public ResponseEntity<?> create(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(friendshipService.create(friendId, principal));
    }

    @GetMapping("/show_incoming_requests")
    public ResponseEntity<?> showIncomingRequests(Principal principal) {
        return ResponseEntity.ok(friendshipService.showIncomingRequests(principal));
    }

    @GetMapping("/show_outgoing_requests")
    public ResponseEntity<?> showOutgoingRequests(Principal principal) {
        return ResponseEntity.ok(friendshipService.showOutgoingRequests(principal));
    }

    @PatchMapping("/accept/{friendId}")
    public ResponseEntity<?> accept(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(friendshipService.accept(friendId, principal));
    }

    @GetMapping("/show")
    public ResponseEntity<?> show(Principal principal) {
        return ResponseEntity.ok(friendshipService.show(principal));
    }

    @GetMapping("/show/{userId}")
    public ResponseEntity<?> show(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.show(userId));
    }

    @DeleteMapping("/delete/{friendId}")
    public ResponseEntity<?> delete(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(friendshipService.delete(friendId, principal));
    }

    @DeleteMapping("/reject/{friendId}")
    public ResponseEntity<?> reject(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(friendshipService.reject(friendId, principal));
    }

    @DeleteMapping("/delete_outgoing_request/{friendId}")
    public ResponseEntity<?> deleteOutgoingRequest(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(friendshipService.deleteOutgoingRequest(friendId, principal));
    }

}
