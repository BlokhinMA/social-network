package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.*;
import ru.sstu.socialnetworkbackend.entities.Photo;
import ru.sstu.socialnetworkbackend.services.PhotoService;

import java.io.ByteArrayInputStream;
import java.security.Principal;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService service;

    public PhotoController(PhotoService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute @Valid PhotoDto photoDto, Principal principal) {
        return ResponseEntity.ok(service.create(photoDto, principal));
    }

    @GetMapping("/show_entity/{id}")
    public ResponseEntity<?> showEntity(@PathVariable Long id) {
        Photo photo = service.showEntity(id);
        return ResponseEntity.ok()
                .header("fileName", photo.getOriginalFileName())
                .contentType(MediaType.valueOf(photo.getContentType()))
                .contentLength(photo.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(photo.getBytes())));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.show(id, principal));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.delete(id, principal));
    }

    @PostMapping("/create_tag")
    public ResponseEntity<?> createTag(@RequestBody @Valid PhotoTagDto dto, Principal principal) {
        return ResponseEntity.ok(service.createTag(dto, principal));
    }

    @DeleteMapping("/delete_tag/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.deleteTag(id, principal));
    }

    @PostMapping("/create_rating")
    public ResponseEntity<?> createRating(@RequestBody @Valid PhotoRatingDto dto, Principal principal) {
        return ResponseEntity.ok(service.createRating(dto, principal));
    }

    @PatchMapping("/update_rating")
    public ResponseEntity<?> updateRating(@RequestBody @Valid PhotoRatingDto dto, Principal principal) {
        return ResponseEntity.ok(service.updateRating(dto, principal));
    }

    @DeleteMapping("/delete_rating/{photoId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long photoId, Principal principal) {
        return ResponseEntity.ok(service.deleteRating(photoId, principal));
    }

    @GetMapping("/user_rating/{photoId}")
    public ResponseEntity<?> userRating(@PathVariable Long photoId, Principal principal) {
        return ResponseEntity.ok(service.userRating(photoId, principal));
    }

    @PostMapping("/create_comment")
    public ResponseEntity<?> createComment(@RequestBody @Valid PhotoCommentDto dto, Principal principal) {
        return ResponseEntity.ok(service.createComment(dto, principal));
    }

    @DeleteMapping("/delete_comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.deleteComment(id, principal));
    }

    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody @Valid FindPhotosDto dto) {
        return ResponseEntity.ok(service.find(dto));
    }

}
