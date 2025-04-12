package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.socialnetwork.dtos.PhotoDto;
import ru.sstu.socialnetwork.entities.Album;
import ru.sstu.socialnetwork.entities.Photo;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.exceptions.EmptyFileException;
import ru.sstu.socialnetwork.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetwork.repositories.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(PhotoService.class);
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final AlbumRepository albumRepository;
    private final UserService userService;

    public PhotoService(PhotoRepository photoRepository,
                        PhotoTagRepository photoTagRepository,
                        PhotoRatingRepository photoRatingRepository,
                        PhotoCommentRepository photoCommentRepository,
                        AlbumRepository albumRepository,
                        UserService userService) {
        this.photoRepository = photoRepository;
        this.photoTagRepository = photoTagRepository;
        this.photoRatingRepository = photoRatingRepository;
        this.photoCommentRepository = photoCommentRepository;
        this.albumRepository = albumRepository;
        this.userService = userService;
    }

    public List<Photo> create(PhotoDto photoDto, Principal principal) {
        log.info(photoDto.getFiles().size());
        if (photoDto.getFiles().getFirst().getSize() == 0)
            throw new EmptyFileException();
        User owner = userService.getCurrentUser(principal);
        Album album = albumRepository.findById(photoDto.getAlbumId())
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
        List<Photo> photos = new ArrayList<>();
        List<Photo> createdPhotos = new ArrayList<>();
        for (int i = 0; i < photoDto.getFiles().size(); i++) {
            photos.add(toPhotoEntity(photoDto.getFiles().get(i)));
            photos.get(i).setAlbum(album);
            Photo createdPhoto = photoRepository.save(photos.get(i));
            createdPhoto.setBytes(null);
            createdPhotos.add(createdPhoto);
        }
        log.info("Пользователь {} добавил фотографии {}",
                owner,
                createdPhotos);
        return createdPhotos;
    }

    private Photo toPhotoEntity(MultipartFile file) {
        Photo photo = new Photo();
        photo.setOriginalFileName(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setContentType(file.getContentType());
        try {
            photo.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(); // todo написать свое исключение
        }
        return photo;
    }

    public Photo show(Long id) {
        return photoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Фотография не найдена"));
    }

    public List<Long> showAll(Long albumId) {
        albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
        return photoRepository.findAllIdByAlbumId(albumId);
    }

}
