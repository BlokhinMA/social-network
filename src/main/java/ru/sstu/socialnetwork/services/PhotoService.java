package ru.sstu.socialnetwork.services;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.socialnetwork.dtos.*;
import ru.sstu.socialnetwork.entities.*;
import ru.sstu.socialnetwork.exceptions.*;
import ru.sstu.socialnetwork.repositories.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
            createdPhotos.add(createdPhoto);
        }
        log.info("Пользователь {} добавил фотографии {}",
                owner,
                createdPhotos);
        return createdPhotos;
    }

    public Photo showEntity(Long id) {
        return getPhotoFromDB(id);
    }

    public PhotoResponseDto show(Long id, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Photo photo = getPhotoFromDB(id);
        User owner = photo.getAlbum().getOwner();
        List<PhotoTag> tags = photoTagRepository.findAllByPhoto(photo);

        List<PhotoComment> commentsFromDb = photoCommentRepository.findAllByPhotoOrderByCommentingTimeStampDesc(photo);
        List<PhotoCommentResponseDto> comments = new ArrayList<>();
        PhotoCommentResponseDto commentDto;
        for (PhotoComment comment : commentsFromDb) {
            commentDto = new PhotoCommentResponseDto(
                    comment,
                    currentUser.equals(comment.getCommentingUser())
            );
            comments.add(commentDto);
        }

        Double rating = rating(id);

        Boolean userRating = photoRatingRepository.userRatingByRatingUserIdAndPhotoId(currentUser.getId(), id);
        Boolean isOwner = currentUser.equals(owner);
        return new PhotoResponseDto(
                photo,
                tags,
                comments,
                rating,
                userRating,
                isOwner
        );
    }

    public List<Long> showAll(Long albumId) {
        albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
        return photoRepository.findAllIdByAlbumId(albumId);
    }

    @Transactional
    public Photo delete(Long id, Principal principal) {
        Photo photo = getPhotoFromDB(id);
        Album album = getAlbumFromDB(photo.getAlbum().getId());
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        photoCommentRepository.deleteAllByPhoto(photo);
        photoRatingRepository.deleteAllByPhoto(photo);
        photoTagRepository.deleteAllByPhoto(photo);
        photoRepository.deleteById(photo.getId());
        log.info("Пользователь {} удалил фотографию {}",
                currentUser,
                photo);
        return photo;
    }

    public PhotoTag createTag(PhotoTagDto dto, Principal principal) {
        Photo photo = getPhotoFromDB(dto.getPhotoId());
        photoTagRepository.findByTagAndPhoto(dto.getTag(), photo)
                .orElseThrow(() -> new ResourceAlreadyExistsException("Такой тег уже существует"));
        Album album = photo.getAlbum();
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        PhotoTag tag = new PhotoTag(
                dto.getTag(),
                photo
        );
        PhotoTag createdTag = photoTagRepository.save(tag);
        log.info("Пользователь {} добавил тег {}",
                currentUser,
                createdTag);
        return createdTag;
    }

    public PhotoTag deleteTag(Long id, Principal principal) {
        PhotoTag tag = photoTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тега не существует"));
        Photo photo = tag.getPhoto();
        Album album = photo.getAlbum();
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        photoTagRepository.deleteById(tag.getId());
        log.info("Пользователь {} удалил тег {}",
                currentUser,
                tag);
        return tag;
    }

    public PhotoRatingResponseDto createRating(PhotoRatingDto dto, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Photo photo = getPhotoFromDB(dto.getPhotoId());
        if (photoRatingRepository.findByRatingUserAndPhoto(currentUser, photo).isPresent()) {
            throw new ResourceAlreadyExistsException("Рейтинг уже существует");
        }
        PhotoRating rating = new PhotoRating(
                dto.getRating(),
                currentUser,
                photo
        );
        PhotoRating createdRating = photoRatingRepository.save(rating);
        PhotoRatingResponseDto responseDto = new PhotoRatingResponseDto(
                createdRating,
                rating(dto.getPhotoId())
        );
        log.info("Пользователь {} поставил оценку фотографии {}",
                currentUser,
                createdRating);
        return responseDto;
    }

    public PhotoRatingResponseDto updateRating(PhotoRatingDto dto, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Photo photo = getPhotoFromDB(dto.getPhotoId());
        PhotoRating rating = getPhotoRatingFromDB(currentUser, photo);
        User ratingUser = rating.getRatingUser();
        checkRights(currentUser, ratingUser);
        rating.setRating(dto.getRating());
        PhotoRating updatedRating = photoRatingRepository.save(rating);
        PhotoRatingResponseDto responseDto = new PhotoRatingResponseDto(
                updatedRating,
                rating(photo.getId())
        );
        log.info("Пользователь {} обновил оценку фотографии {}",
                currentUser,
                updatedRating);
        return responseDto;
    }

    public PhotoRatingResponseDto deleteRating(Long photoId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Photo photo = getPhotoFromDB(photoId);
        PhotoRating rating = getPhotoRatingFromDB(currentUser, photo);
        User ratingUser = rating.getRatingUser();
        checkRights(currentUser, ratingUser);
        photoRatingRepository.deleteById(rating.getId());
        PhotoRatingResponseDto responseDto = new PhotoRatingResponseDto(
                rating,
                rating(rating.getPhoto().getId())
        );
        log.info("Пользователь {} удалил оценку фотографии {}",
                currentUser,
                rating);
        return responseDto;
    }

    public Double rating(Long photoId) {
        Double rating = photoRatingRepository.ratingByPhotoId(photoId);
        if (rating != null)
            return (double) Math.round(rating);
        else return null;
    }

    public UserRatingResponseDto userRating(Long photoId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Photo photo = getPhotoFromDB(photoId);
        return new UserRatingResponseDto(
                photoRatingRepository.userRatingByRatingUserIdAndPhotoId(currentUser.getId(), photo.getId())
        );
    }

    public PhotoComment createComment(PhotoCommentDto dto, Principal principal) {
        Photo photo = getPhotoFromDB(dto.getPhotoId());
        User currentUser = userService.getCurrentUser(principal);
        PhotoComment comment = new PhotoComment(
                dto.getComment(),
                currentUser,
                photo
        );
        PhotoComment createdComment = photoCommentRepository.save(comment);
        log.info("Пользователь {} добавил комментарий {}",
                currentUser,
                createdComment);
        return createdComment;
    }

    public PhotoComment deleteComment(Long id, Principal principal) {
        PhotoComment comment = photoCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Комментарий не существует"));
        User currentUser = userService.getCurrentUser(principal);
        User commentingUser = comment.getCommentingUser();
        checkRights(currentUser, commentingUser);
        photoCommentRepository.deleteById(id);
        log.info("Пользователь {} удалил комментарий {}",
                currentUser,
                comment);
        return comment;
    }

    public List<Long> find(FindPhotosDto dto) {
        if (dto.getKeyword().isEmpty()) {
            throw new IncorrectKeywordException();
        }
        switch (dto.getSearchTerm()) {
            case "creationTimeStamp":
                LocalDateTime creationTimeStamp;
                try {
                    creationTimeStamp = LocalDateTime.parse(dto.getKeyword());
                } catch (DateTimeParseException e) {
                    throw new IncorrectSearchTermException();
                }
                return photoRepository.findAllIdByCreationTimeStamp(creationTimeStamp);
            case "tag":
                return photoRepository.findAllIdLikeTag(dto.getKeyword());
            case "comment":
                return photoRepository.findAllIdLikeComment(dto.getKeyword());
            default:
                throw new IncorrectSearchTermException();
        }
    }

    public void deleteAllByAlbum(Album album, Principal principal) {
        List<Photo> photos = photoRepository.findAllIdByAlbum(album);
        for (Photo photo : photos) {
            delete(photo.getId(), principal);
        }
    }

    private Photo getPhotoFromDB(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фотография не найдена"));
    }

    private void checkRights(User currentUser, User user) {
        if (!currentUser.equals(user)) {
            throw new AccessDeniedException("У Вас недостаточно прав на выполнение данной операции");
        }
    }

    private PhotoRating getPhotoRatingFromDB(User ratingUser, Photo photo) {
        return photoRatingRepository.findByRatingUserAndPhoto(ratingUser, photo)
                .orElseThrow(() -> new ResourceNotFoundException("Рейтинга не существует"));
    }

    private Album getAlbumFromDB(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
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

}
