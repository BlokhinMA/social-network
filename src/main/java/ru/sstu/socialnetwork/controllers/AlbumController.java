package ru.sstu.socialnetwork.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetwork.dtos.AlbumDto;
import ru.sstu.socialnetwork.dtos.ChangeAlbumAccessTypeDto;
import ru.sstu.socialnetwork.services.AlbumService;

import java.security.Principal;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody AlbumDto albumDto, Principal principal) {
        return ResponseEntity.ok(albumService.create(albumDto, principal));
    }

    @GetMapping("/show_mine")
    public ResponseEntity<?> showMine(Principal principal) {
        return ResponseEntity.ok(albumService.showAll(principal));
    }

    @GetMapping("/show_all/{ownerId}")
    public ResponseEntity<?> showAll(@PathVariable Long ownerId) {
        return ResponseEntity.ok(albumService.showAll(ownerId));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(albumService.show(id, principal));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(albumService.delete(id, principal));
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<?> find(@PathVariable String keyword) {
        return ResponseEntity.ok(albumService.find(keyword));
    }

    @PatchMapping("/change_access_type")
    public ResponseEntity<?> changeAccessType(@RequestBody ChangeAlbumAccessTypeDto dto, Principal principal) {
        return ResponseEntity.ok(albumService.changeAccessType(dto, principal));
    }

}
