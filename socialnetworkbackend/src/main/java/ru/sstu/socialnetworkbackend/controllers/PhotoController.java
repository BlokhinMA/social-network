package ru.sstu.socialnetworkbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetworkbackend.dtos.photos.*;
import ru.sstu.socialnetworkbackend.entities.Photo;
import ru.sstu.socialnetworkbackend.services.PhotoService;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {

    private final PhotoService service;

    public PhotoController(PhotoService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute @Valid PhotoDto photoDto) {
        return ResponseEntity.ok(service.create(photoDto));
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
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(service.show(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping("/create_tag")
    public ResponseEntity<?> createTag(@RequestBody @Valid PhotoTagDto dto) {
        return ResponseEntity.ok(service.createTag(dto));
    }

    @DeleteMapping("/delete_tag/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteTag(id));
    }

    @PostMapping("/create_rating")
    public ResponseEntity<?> createRating(@RequestBody @Valid PhotoRatingDto dto) {
        return ResponseEntity.ok(service.createRating(dto));
    }

    @PatchMapping("/update_rating")
    public ResponseEntity<?> updateRating(@RequestBody @Valid PhotoRatingDto dto) {
        return ResponseEntity.ok(service.updateRating(dto));
    }

    @DeleteMapping("/delete_rating/{photoId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long photoId) {
        return ResponseEntity.ok(service.deleteRating(photoId));
    }

    @GetMapping("/user_rating/{photoId}")
    public ResponseEntity<?> userRating(@PathVariable Long photoId) {
        return ResponseEntity.ok(service.userRating(photoId));
    }

    @PostMapping("/create_comment")
    public ResponseEntity<?> createComment(@RequestBody @Valid PhotoCommentDto dto) {
        return ResponseEntity.ok(service.createComment(dto));
    }

    @DeleteMapping("/delete_comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteComment(id));
    }

    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody @Valid FindPhotosDto dto) {
        return ResponseEntity.ok(service.find(dto));
    }

}
