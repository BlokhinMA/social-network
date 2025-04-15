package ru.sstu.socialnetwork.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetwork.dtos.CommunityDto;
import ru.sstu.socialnetwork.dtos.CommunityPostDto;
import ru.sstu.socialnetwork.services.CommunityService;

import java.security.Principal;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/show_mine")
    public ResponseEntity<?> showMine(Principal principal) {
        return ResponseEntity.ok(communityService.showAllOwn(principal));
    }

    @GetMapping("/show_my_subscriptions")
    public ResponseEntity<?> showMySubscriptions(Principal principal) {
        return ResponseEntity.ok(communityService.showAll(principal));
    }

    @GetMapping("/show_subscriptions/{memberId}")
    public ResponseEntity<?> showSubscriptions(@PathVariable Long memberId) {
        return ResponseEntity.ok(communityService.showAll(memberId));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(communityService.show(id, principal));
    }

//    @GetMapping("/show_all_members/{communityId}")
//    public ResponseEntity<?> showAllMembers(@PathVariable Long communityId) {
//        return ResponseEntity.ok(communityService.showAllMembers(communityId));
//    }
//
//    @GetMapping("/show_all_posts/{communityId}")
//    public ResponseEntity<?> showAllPosts(@PathVariable Long communityId) {
//        return ResponseEntity.ok(communityService.showAllPosts(communityId));
//    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CommunityDto dto, Principal principal) {
        return ResponseEntity.ok(communityService.create(dto, principal));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(communityService.delete(id, principal));
    }

    @GetMapping("/join/{communityId}")
    public ResponseEntity<?> join(@PathVariable Long communityId, Principal principal) {
        return ResponseEntity.ok(communityService.join(communityId, principal));
    }

//    @GetMapping("/is_member/{communityId}")
//    public ResponseEntity<?> isMember(Principal principal, @PathVariable Long communityId) {
//        return ResponseEntity.ok(communityService.checkIsMember(principal, communityId));
//    }
//
//    @GetMapping("/is_creator/{id}")
//    public ResponseEntity<?> isCreator(@PathVariable Long id, Principal principal) {
//        return ResponseEntity.ok(communityService.checkIsCreator(id, principal));
//    }

    @DeleteMapping("/leave/{communityId}")
    public ResponseEntity<?> leave(@PathVariable Long communityId, Principal principal) {
        return ResponseEntity.ok(communityService.leave(communityId, principal));
    }

    @DeleteMapping("/kick/{id}")
    public ResponseEntity<?> kick(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(communityService.kick(id, principal));
    }

    @PostMapping("/create_post")
    public ResponseEntity<?> createPost(@Valid @RequestBody CommunityPostDto communityPostDto, Principal principal) {
        return ResponseEntity.ok(communityService.createPost(communityPostDto, principal));
    }

    @DeleteMapping("/delete_post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(communityService.deletePost(id, principal));
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<?> find(@PathVariable String keyword) {
        return ResponseEntity.ok(communityService.find(keyword));
    }

}
