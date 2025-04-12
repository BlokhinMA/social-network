package ru.sstu.socialnetwork.controllers;

import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.socialnetwork.dtos.PhotoDto;
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

    @GetMapping("/show/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Photo photo = photoService.show(id);
        return ResponseEntity.ok()
                .header("fileName", photo.getOriginalFileName())
                .contentType(MediaType.valueOf(photo.getContentType()))
                .contentLength(photo.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(photo.getBytes())));
    }

    @GetMapping("/show_all/{albumId}")
    public ResponseEntity<?> showAll(@PathVariable Long albumId) {
        return ResponseEntity.ok(photoService.showAll(albumId));
    }

}
