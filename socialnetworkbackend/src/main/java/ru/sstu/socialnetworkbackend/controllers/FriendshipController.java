package ru.sstu.socialnetworkbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.services.FriendshipService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/friendships")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<?> find(@PathVariable String keyword, Principal principal) {
        return ResponseEntity.ok(service.find(keyword, principal));
    }

    @GetMapping("/create/{friendId}")
    public ResponseEntity<?> create(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(service.create(friendId, principal));
    }

    @GetMapping("/show_incoming_requests")
    public ResponseEntity<?> showIncomingRequests(Principal principal) {
        return ResponseEntity.ok(service.showIncomingRequests(principal));
    }

    @GetMapping("/show_outgoing_requests")
    public ResponseEntity<?> showOutgoingRequests(Principal principal) {
        return ResponseEntity.ok(service.showOutgoingRequests(principal));
    }

    @PatchMapping("/accept/{friendId}")
    public ResponseEntity<?> accept(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(service.accept(friendId, principal));
    }

    @GetMapping("/show_mine")
    public ResponseEntity<?> show(Principal principal) {
        return ResponseEntity.ok(service.show(principal));
    }

    @GetMapping("/show/{userId}")
    public ResponseEntity<?> show(@PathVariable Long userId) {
        return ResponseEntity.ok(service.show(userId));
    }

    @DeleteMapping("/delete/{friendId}")
    public ResponseEntity<?> delete(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(service.delete(friendId, principal));
    }

    @DeleteMapping("/reject/{friendId}")
    public ResponseEntity<?> reject(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(service.reject(friendId, principal));
    }

    @DeleteMapping("/delete_outgoing_request/{friendId}")
    public ResponseEntity<?> deleteOutgoingRequest(@PathVariable Long friendId, Principal principal) {
        return ResponseEntity.ok(service.deleteOutgoingRequest(friendId, principal));
    }

}
