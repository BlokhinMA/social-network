package ru.sstu.socialnetwork.restControllers;

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

    @GetMapping("/show_subscriptions/{memberLogin}")
    public ResponseEntity<?> showSubscriptions(@PathVariable("member_login") String memberLogin) {
        return ResponseEntity.ok(communityService.showAll(memberLogin));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.show(id));
    }

    @GetMapping("/show_all_members/{communityId}")
    public ResponseEntity<?> showAllMembers(@PathVariable Long communityId) {
        return ResponseEntity.ok(communityService.showAllMembers(communityId));
    }

    @GetMapping("/show_all_posts/{communityId}")
    public ResponseEntity<?> showAllPosts(@PathVariable Long communityId) {
        return ResponseEntity.ok(communityService.showAllPosts(communityId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CommunityDto communityDto, Principal principal) {
        return ResponseEntity.ok(communityService.create(communityDto, principal));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(communityService.delete(id, principal));
    }

    @GetMapping("/join/{communityId}")
    public ResponseEntity<?> join(Principal principal, @PathVariable Long communityId) {
        return ResponseEntity.ok(communityService.join(principal, communityId));
    }

    @GetMapping("/is_member/{communityId}")
    public ResponseEntity<?> isMember(Principal principal, @PathVariable Long communityId) {
        return ResponseEntity.ok(communityService.isMember(principal, communityId));
    }

    @DeleteMapping("/leave/{communityId}")
    public ResponseEntity<?> leave(Principal principal, @PathVariable Long communityId) {
        return ResponseEntity.ok(communityService.leave(principal, communityId));
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

    @GetMapping("/find/{word}")
    public ResponseEntity<?> find(@PathVariable String word) {
        return ResponseEntity.ok(communityService.find(word));
    }

    @GetMapping("/show_all")
    public ResponseEntity<?> showAll() {
        return ResponseEntity.ok(communityService.showAll());
    }

    @GetMapping("/show_all_members")
    public ResponseEntity<?> showAllMembers() {
        return ResponseEntity.ok(communityService.showAllMembers());
    }

    @GetMapping("/show_all_posts")
    public ResponseEntity<?> showAllPosts() {
        return ResponseEntity.ok(communityService.showAllPosts());
    }

}
