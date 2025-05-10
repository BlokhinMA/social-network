package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunityDto;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunityPostDto;
import ru.sstu.socialnetworkbackend.services.CommunityService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityService service;

    public CommunityController(CommunityService service) {
        this.service = service;
    }

    @GetMapping("/show_mine")
    public ResponseEntity<?> showMine(Principal principal) {
        return ResponseEntity.ok(service.showAllOwn(principal));
    }

    @GetMapping("/show_my_subscriptions")
    public ResponseEntity<?> showMySubscriptions(Principal principal) {
        return ResponseEntity.ok(service.showAll(principal));
    }

    @GetMapping("/show_subscriptions/{memberId}")
    public ResponseEntity<?> showSubscriptions(@PathVariable Long memberId) {
        return ResponseEntity.ok(service.showAll(memberId));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.show(id, principal));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CommunityDto dto, Principal principal) {
        return ResponseEntity.ok(service.create(dto, principal));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.delete(id, principal));
    }

    @GetMapping("/join/{communityId}")
    public ResponseEntity<?> join(@PathVariable Long communityId, Principal principal) {
        return ResponseEntity.ok(service.join(communityId, principal));
    }

    @DeleteMapping("/leave/{communityId}")
    public ResponseEntity<?> leave(@PathVariable Long communityId, Principal principal) {
        return ResponseEntity.ok(service.leave(communityId, principal));
    }

    @DeleteMapping("/kick/{id}")
    public ResponseEntity<?> kick(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.kick(id, principal));
    }

    @PostMapping("/create_post")
    public ResponseEntity<?> createPost(@Valid @RequestBody CommunityPostDto communityPostDto, Principal principal) {
        return ResponseEntity.ok(service.createPost(communityPostDto, principal));
    }

    @DeleteMapping("/delete_post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.deletePost(id, principal));
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<?> find(@PathVariable String keyword) {
        return ResponseEntity.ok(service.find(keyword));
    }

}
