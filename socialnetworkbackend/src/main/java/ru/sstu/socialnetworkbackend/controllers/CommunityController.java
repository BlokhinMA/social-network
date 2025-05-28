package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunitiesResponseDto;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunityDto;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunityPostDto;
import ru.sstu.socialnetworkbackend.dtos.communities.CommunityResponseDto;
import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.CommunityMember;
import ru.sstu.socialnetworkbackend.entities.CommunityPost;
import ru.sstu.socialnetworkbackend.services.CommunityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityService service;

    public CommunityController(CommunityService service) {
        this.service = service;
    }

    @GetMapping("/show_mine")
    public ResponseEntity<List<Community>> showMine() {
        return ResponseEntity.ok(service.showAllOwn());
    }

    @GetMapping("/show_my_subscriptions")
    public ResponseEntity<List<Community>> showMySubscriptions() {
        return ResponseEntity.ok(service.showAll());
    }

    @GetMapping("/show_subscriptions/{memberId}")
    public ResponseEntity<CommunitiesResponseDto> showSubscriptions(@PathVariable Long memberId) {
        return ResponseEntity.ok(service.showAll(memberId));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<CommunityResponseDto> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Community> create(@RequestBody @Valid CommunityDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Community> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/join/{communityId}")
    public ResponseEntity<CommunityMember> join(@PathVariable Long communityId) {
        return ResponseEntity.ok(service.join(communityId));
    }

    @DeleteMapping("/leave/{communityId}")
    public ResponseEntity<CommunityMember> leave(@PathVariable Long communityId) {
        return ResponseEntity.ok(service.leave(communityId));
    }

    @DeleteMapping("/kick/{id}")
    public ResponseEntity<CommunityMember> kick(@PathVariable Long id) {
        return ResponseEntity.ok(service.kick(id));
    }

    @PostMapping("/create_post")
    public ResponseEntity<CommunityPost> createPost(@Valid @RequestBody CommunityPostDto communityPostDto) {
        return ResponseEntity.ok(service.createPost(communityPostDto));
    }

    @DeleteMapping("/delete_post/{id}")
    public ResponseEntity<CommunityPost> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletePost(id));
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<List<Community>> find(@PathVariable String keyword) {
        return ResponseEntity.ok(service.find(keyword));
    }

}
