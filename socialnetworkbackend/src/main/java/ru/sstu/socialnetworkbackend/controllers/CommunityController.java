package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunityDto;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunityPostDto;
import ru.sstu.socialnetworkbackend.services.CommunityService;

@RestController
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityService service;

    public CommunityController(CommunityService service) {
        this.service = service;
    }

    @GetMapping("/show_mine")
    public ResponseEntity<?> showMine() {
        return ResponseEntity.ok(service.showAllOwn());
    }

    @GetMapping("/show_my_subscriptions")
    public ResponseEntity<?> showMySubscriptions() {
        return ResponseEntity.ok(service.showAll());
    }

    @GetMapping("/show_subscriptions/{memberId}")
    public ResponseEntity<?> showSubscriptions(@PathVariable Long memberId) {
        return ResponseEntity.ok(service.showAll(memberId));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CommunityDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/join/{communityId}")
    public ResponseEntity<?> join(@PathVariable Long communityId) {
        return ResponseEntity.ok(service.join(communityId));
    }

    @DeleteMapping("/leave/{communityId}")
    public ResponseEntity<?> leave(@PathVariable Long communityId) {
        return ResponseEntity.ok(service.leave(communityId));
    }

    @DeleteMapping("/kick/{id}")
    public ResponseEntity<?> kick(@PathVariable Long id) {
        return ResponseEntity.ok(service.kick(id));
    }

    @PostMapping("/create_post")
    public ResponseEntity<?> createPost(@Valid @RequestBody CommunityPostDto communityPostDto) {
        return ResponseEntity.ok(service.createPost(communityPostDto));
    }

    @DeleteMapping("/delete_post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletePost(id));
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<?> find(@PathVariable String keyword) {
        return ResponseEntity.ok(service.find(keyword));
    }

}
