package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.AlbumDto;
import ru.sstu.socialnetworkbackend.dtos.ChangeAlbumAccessTypeDto;
import ru.sstu.socialnetworkbackend.services.AlbumService;

import java.security.Principal;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @ModelAttribute AlbumDto albumDto, Principal principal) {
        return ResponseEntity.ok(service.create(albumDto, principal));
    }

    @GetMapping("/show_mine")
    public ResponseEntity<?> showMine(Principal principal) {
        return ResponseEntity.ok(service.showAll(principal));
    }

    @GetMapping("/show_all/{ownerId}")
    public ResponseEntity<?> showAll(@PathVariable Long ownerId) {
        return ResponseEntity.ok(service.showAll(ownerId));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.show(id, principal));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.delete(id, principal));
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<?> find(@PathVariable String keyword) {
        return ResponseEntity.ok(service.find(keyword));
    }

    @PatchMapping("/change_access_type")
    public ResponseEntity<?> changeAccessType(@RequestBody @Valid ChangeAlbumAccessTypeDto dto, Principal principal) {
        return ResponseEntity.ok(service.changeAccessType(dto, principal));
    }

}
