package ru.sstu.socialnetworkbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.friends.FriendsResponseDto;
import ru.sstu.socialnetworkbackend.entities.Friendship;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.services.FriendshipService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friendships")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<List<User>> find(@PathVariable String keyword) {
        return ResponseEntity.ok(service.find(keyword));
    }

    @GetMapping("/create/{friendId}")
    public ResponseEntity<Friendship> create(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.create(friendId));
    }

    @GetMapping("/show_incoming_requests")
    public ResponseEntity<List<User>> showIncomingRequests() {
        return ResponseEntity.ok(service.showIncomingRequests());
    }

    @GetMapping("/show_outgoing_requests")
    public ResponseEntity<List<User>> showOutgoingRequests() {
        return ResponseEntity.ok(service.showOutgoingRequests());
    }

    @PatchMapping("/accept/{friendId}")
    public ResponseEntity<Friendship> accept(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.accept(friendId));
    }

    @GetMapping("/show_mine")
    public ResponseEntity<List<User>> show() {
        return ResponseEntity.ok(service.show());
    }

    @GetMapping("/show/{userId}")
    public ResponseEntity<FriendsResponseDto> show(@PathVariable Long userId) {
        return ResponseEntity.ok(service.show(userId));
    }

    @DeleteMapping("/delete/{friendId}")
    public ResponseEntity<Friendship> delete(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.delete(friendId));
    }

    @DeleteMapping("/reject/{friendId}")
    public ResponseEntity<Friendship> reject(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.reject(friendId));
    }

    @DeleteMapping("/delete_outgoing_request/{friendId}")
    public ResponseEntity<Friendship> deleteOutgoingRequest(@PathVariable Long friendId) {
        return ResponseEntity.ok(service.deleteOutgoingRequest(friendId));
    }

}
