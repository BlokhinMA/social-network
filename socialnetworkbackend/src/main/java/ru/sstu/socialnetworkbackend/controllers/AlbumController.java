package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.albums.AlbumDto;
import ru.sstu.socialnetworkbackend.dtos.albums.AlbumResponseDto;
import ru.sstu.socialnetworkbackend.dtos.albums.AlbumsResponseDto;
import ru.sstu.socialnetworkbackend.dtos.albums.ChangeAlbumAccessTypeDto;
import ru.sstu.socialnetworkbackend.entities.Album;
import ru.sstu.socialnetworkbackend.services.AlbumService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Album> create(@Valid @ModelAttribute AlbumDto albumDto) {
        return ResponseEntity.ok(service.create(albumDto));
    }

    @GetMapping("/show_mine")
    public ResponseEntity<List<Album>> showMine() {
        return ResponseEntity.ok(service.showAll());
    }

    @GetMapping("/show_all/{ownerId}")
    public ResponseEntity<AlbumsResponseDto> showAll(@PathVariable Long ownerId) {
        return ResponseEntity.ok(service.showAll(ownerId));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<AlbumResponseDto> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Album> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/find/{keyword}")
    public ResponseEntity<List<Album>> find(@PathVariable String keyword) {
        return ResponseEntity.ok(service.find(keyword));
    }

    @PatchMapping("/change_access_type")
    public ResponseEntity<Album> changeAccessType(@RequestBody @Valid ChangeAlbumAccessTypeDto dto) {
        return ResponseEntity.ok(service.changeAccessType(dto));
    }

}
