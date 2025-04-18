package ru.sstu.socialnetwork.controllers;

import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetwork.dtos.*;
import ru.sstu.socialnetwork.entities.Photo;
import ru.sstu.socialnetwork.services.PhotoService;

import java.io.ByteArrayInputStream;
import java.security.Principal;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute @Valid PhotoDto photoDto, Principal principal) {
        return ResponseEntity.ok(photoService.create(photoDto, principal));
    }

    @GetMapping("/show_entity/{id}")
    public ResponseEntity<?> showEntity(@PathVariable Long id) {
        Photo photo = photoService.showEntity(id);
        return ResponseEntity.ok()
                .header("fileName", photo.getOriginalFileName())
                .contentType(MediaType.valueOf(photo.getContentType()))
                .contentLength(photo.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(photo.getBytes())));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(photoService.show(id, principal));
    }

//    @GetMapping("/show_all/{albumId}")
//    public ResponseEntity<?> showAll(@PathVariable Long albumId) {
//        return ResponseEntity.ok(photoService.showAll(albumId));
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(photoService.delete(id, principal));
    }

    @PostMapping("/create_tag")
    public ResponseEntity<?> createTag(@RequestBody @Valid PhotoTagDto dto, Principal principal) {
        return ResponseEntity.ok(photoService.createTag(dto, principal));
    }

    @DeleteMapping("/delete_tag/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(photoService.deleteTag(id, principal));
    }

    @PostMapping("/create_rating")
    public ResponseEntity<?> createRating(@RequestBody @Valid PhotoRatingDto dto, Principal principal) {
        return ResponseEntity.ok(photoService.createRating(dto, principal));
    }

    @PatchMapping("/update_rating")
    public ResponseEntity<?> updateRating(@RequestBody @Valid PhotoRatingDto dto, Principal principal) {
        return ResponseEntity.ok(photoService.updateRating(dto, principal));
    }

    @DeleteMapping("/delete_rating/{photoId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long photoId, Principal principal) {
        return ResponseEntity.ok(photoService.deleteRating(photoId, principal));
    }

    @GetMapping("/user_rating/{photoId}")
    public ResponseEntity<?> userRating(@PathVariable Long photoId, Principal principal) {
        return ResponseEntity.ok(photoService.userRating(photoId, principal));
    }

//    @GetMapping("/rating/{photoId}")
//    public ResponseEntity<?> rating(@PathVariable Long photoId) {
//        return ResponseEntity.ok(photoService.rating(photoId));
//    }

    @PostMapping("/create_comment")
    public ResponseEntity<?> createComment(@RequestBody @Valid PhotoCommentDto dto, Principal principal) {
        return ResponseEntity.ok(photoService.createComment(dto, principal));
    }

    @DeleteMapping("/delete_comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(photoService.deleteComment(id, principal));
    }

    @GetMapping("/find")
    public ResponseEntity<?> find(@RequestBody FindPhotosDto dto) {
        return ResponseEntity.ok(photoService.find(dto));
    }

}
