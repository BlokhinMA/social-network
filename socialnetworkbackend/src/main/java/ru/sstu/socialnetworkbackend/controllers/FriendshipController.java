package ru.sstu.socialnetworkbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.services.FriendshipService;

@RestController
@RequestMapping("/api/v1/friendships")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<?> find(@PathVariable String keyword) {
        return ResponseEntity.ok(service.find(keyword));
    }

    @GetMapping("/create/{friendId}")
    public ResponseEntity<?> create(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.create(friendId));
    }

    @GetMapping("/show_incoming_requests")
    public ResponseEntity<?> showIncomingRequests() {
        return ResponseEntity.ok(service.showIncomingRequests());
    }

    @GetMapping("/show_outgoing_requests")
    public ResponseEntity<?> showOutgoingRequests() {
        return ResponseEntity.ok(service.showOutgoingRequests());
    }

    @PatchMapping("/accept/{friendId}")
    public ResponseEntity<?> accept(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.accept(friendId));
    }

    @GetMapping("/show_mine")
    public ResponseEntity<?> show() {
        return ResponseEntity.ok(service.show());
    }

    @GetMapping("/show/{userId}")
    public ResponseEntity<?> show(@PathVariable Long userId) {
        return ResponseEntity.ok(service.show(userId));
    }

    @DeleteMapping("/delete/{friendId}")
    public ResponseEntity<?> delete(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.delete(friendId));
    }

    @DeleteMapping("/reject/{friendId}")
    public ResponseEntity<?> reject(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.reject(friendId));
    }

    @DeleteMapping("/delete_outgoing_request/{friendId}")
    public ResponseEntity<?> deleteOutgoingRequest(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.deleteOutgoingRequest(friendId));
    }

}
