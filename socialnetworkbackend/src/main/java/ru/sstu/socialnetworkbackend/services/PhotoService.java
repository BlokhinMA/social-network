package ru.sstu.socialnetworkbackend.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.socialnetworkbackend.dtos.photos.*;
import ru.sstu.socialnetworkbackend.entities.*;
import ru.sstu.socialnetworkbackend.entities.enums.AccessType;
import ru.sstu.socialnetworkbackend.exceptions.*;
import ru.sstu.socialnetworkbackend.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PhotoService extends SuperService {

    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final AlbumRepository albumRepository;
    private final FriendshipService friendshipService;
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(PhotoService.class);

    private static final long FILE_MAX_SIZE = 10_485_760;

    public PhotoService(PhotoRepository photoRepository,
                        PhotoTagRepository photoTagRepository,
                        PhotoRatingRepository photoRatingRepository,
                        PhotoCommentRepository photoCommentRepository,
                        AlbumRepository albumRepository, FriendshipService friendshipService,
                        UserService userService) {
        this.photoRepository = photoRepository;
        this.photoTagRepository = photoTagRepository;
        this.photoRatingRepository = photoRatingRepository;
        this.photoCommentRepository = photoCommentRepository;
        this.albumRepository = albumRepository;
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    public List<Photo> create(PhotoDto photoDto) {
        List<MultipartFile> files = photoDto.files();
        if (files.getFirst().getSize() == 0)
            throw new EmptyFileException();
        for (MultipartFile file : files) {
            if (file.getSize() > FILE_MAX_SIZE)
                throw new MaxFileSizeExceededException();
            checkContentType(file);
        }
        User currentUser = userService.getCurrentUser();
        Album album = albumRepository.findById(photoDto.albumId())
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
        checkRights(currentUser, album.getOwner());
        List<Photo> photos = new ArrayList<>();
        List<Photo> createdPhotos = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            photos.add(toPhotoEntity(files.get(i)));
            photos.get(i).setAlbum(album);
            Photo createdPhoto = photoRepository.save(photos.get(i));
            createdPhotos.add(createdPhoto);
        }
        LOG.info("Пользователь {} добавил фотографии {}",
                currentUser,
                createdPhotos);
        return createdPhotos;
    }

    public Photo showEntity(Long id) {
        return getPhotoFromDB(id);
    }

    public PhotoResponseDto show(Long id) {
        User currentUser = userService.getCurrentUser();
        Photo photo = getPhotoFromDB(id);
        Album album = photo.getAlbum();
        User owner = album.getOwner();

        if (!currentUser.equals(owner) &&
                album.getAccessType() == AccessType.FRIENDS &&
                !friendshipService.areFriends(currentUser.getId(), owner.getId()))
            throw new AccessDeniedException("Данная фотография доступна только друзьям ее владельца");

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
        return photoRepository.findAllIdByAlbumId(albumId);
    }

    @Transactional
    public Photo delete(Long id) {
        Photo photo = getPhotoFromDB(id);
        Album album = getAlbumFromDB(photo.getAlbum().getId());
        User currentUser = userService.getCurrentUser();
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        photoCommentRepository.deleteAllByPhotoId(photo.getId());
        photoRatingRepository.deleteAllByPhotoId(photo.getId());
        photoTagRepository.deleteAllByPhotoId(photo.getId());
        photoRepository.deleteById(photo.getId());
        LOG.info("Пользователь {} удалил фотографию {}",
                currentUser,
                photo);
        return photo;
    }

    @Transactional
    public void deleteAllByAlbumId(Long albumId) {
        List<Long> photosIds = photoRepository.findAllIdByAlbumId(albumId);
        for (Long id : photosIds) {
            delete(id);
        }
    }

    public PhotoTag createTag(PhotoTagDto dto) {
        Photo photo = getPhotoFromDB(dto.photoId());
        if (photoTagRepository.findByTagAndPhoto(dto.tag(), photo).isPresent()) {
            throw new ResourceAlreadyExistsException("Такой тег уже существует");
        }
        Album album = photo.getAlbum();
        User currentUser = userService.getCurrentUser();
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        PhotoTag tag = new PhotoTag(
                dto.tag(),
                photo
        );
        PhotoTag createdTag = photoTagRepository.save(tag);
        LOG.info("Пользователь {} добавил тег {}",
                currentUser,
                createdTag);
        return createdTag;
    }

    public PhotoTag deleteTag(Long id) {
        PhotoTag tag = photoTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тега не существует"));
        Photo photo = tag.getPhoto();
        Album album = photo.getAlbum();
        User currentUser = userService.getCurrentUser();
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        photoTagRepository.deleteById(tag.getId());
        LOG.info("Пользователь {} удалил тег {}",
                currentUser,
                tag);
        return tag;
    }

    public PhotoRatingResponseDto createRating(PhotoRatingDto dto) {
        User currentUser = userService.getCurrentUser();
        Photo photo = getPhotoFromDB(dto.photoId());
        if (photoRatingRepository.findByRatingUserAndPhoto(currentUser, photo).isPresent()) {
            throw new ResourceAlreadyExistsException("Рейтинг уже существует");
        }
        PhotoRating rating = new PhotoRating(
                dto.rating(),
                currentUser,
                photo
        );
        PhotoRating createdRating = photoRatingRepository.save(rating);
        PhotoRatingResponseDto responseDto = new PhotoRatingResponseDto(
                createdRating,
                rating(dto.photoId())
        );
        LOG.info("Пользователь {} поставил оценку фотографии {}",
                currentUser,
                createdRating);
        return responseDto;
    }

    public PhotoRatingResponseDto updateRating(PhotoRatingDto dto) {
        User currentUser = userService.getCurrentUser();
        Photo photo = getPhotoFromDB(dto.photoId());
        PhotoRating rating = getPhotoRatingFromDB(currentUser, photo);
        User ratingUser = rating.getRatingUser();
        checkRights(currentUser, ratingUser);
        rating.setRating(dto.rating());
        PhotoRating updatedRating = photoRatingRepository.save(rating);
        PhotoRatingResponseDto responseDto = new PhotoRatingResponseDto(
                updatedRating,
                rating(photo.getId())
        );
        LOG.info("Пользователь {} обновил оценку фотографии {}",
                currentUser,
                updatedRating);
        return responseDto;
    }

    public PhotoRatingResponseDto deleteRating(Long photoId) {
        User currentUser = userService.getCurrentUser();
        Photo photo = getPhotoFromDB(photoId);
        PhotoRating rating = getPhotoRatingFromDB(currentUser, photo);
        User ratingUser = rating.getRatingUser();
        checkRights(currentUser, ratingUser);
        photoRatingRepository.deleteById(rating.getId());
        PhotoRatingResponseDto responseDto = new PhotoRatingResponseDto(
                rating,
                rating(rating.getPhoto().getId())
        );
        LOG.info("Пользователь {} удалил оценку фотографии {}",
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

    public UserRatingResponseDto userRating(Long photoId) {
        User currentUser = userService.getCurrentUser();
        Photo photo = getPhotoFromDB(photoId);
        return new UserRatingResponseDto(
                photoRatingRepository.userRatingByRatingUserIdAndPhotoId(currentUser.getId(), photo.getId())
        );
    }

    public PhotoComment createComment(PhotoCommentDto dto) {
        Photo photo = getPhotoFromDB(dto.photoId());
        User currentUser = userService.getCurrentUser();
        PhotoComment comment = new PhotoComment(
                dto.comment(),
                currentUser,
                photo
        );
        PhotoComment createdComment = photoCommentRepository.save(comment);
        LOG.info("Пользователь {} добавил комментарий {}",
                currentUser,
                createdComment);
        return createdComment;
    }

    public PhotoComment deleteComment(Long id) {
        PhotoComment comment = photoCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Комментарий не существует"));
        User currentUser = userService.getCurrentUser();
        User commentingUser = comment.getCommentingUser();
        checkRights(currentUser, commentingUser);
        photoCommentRepository.deleteById(id);
        LOG.info("Пользователь {} удалил комментарий {}",
                currentUser,
                comment);
        return comment;
    }

    public List<Long> find(FindPhotosDto dto) {
        String keyword = dto.keyword();
        return switch (dto.searchTerm()) {
            case "creationTimeStamp" -> {
                if (!keyword.matches("(\\d{4})-(0[1-9]|1[0-2]|[1-9])-([1-9]|0[1-9]|[1-2]\\d|3[0-1])"))
                    throw new IncorrectKeywordException("Указанная дата должна быть корректной и в формате гггг-мм-дд");
                yield photoRepository.findAllIdLikeCreationTimeStamp(keyword);
            }
            case "tag" -> photoRepository.findAllIdILikeTag(keyword);
            case "comment" -> photoRepository.findAllIdILikeComment(keyword);
            default -> throw new IncorrectSearchTermException();
        };

    }

    private Photo getPhotoFromDB(Long id) {
        return photoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фотография не найдена"));
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
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return photo;
    }

    private void checkContentType(MultipartFile file) {
        String[] acceptableContentTypes = {"image/xbm", "image/tif", "image/jfif", "image/pjp", "image/apng",
                "image/jpeg", "image/heif", "image/ico", "image/tiff", "image/webp", "image/svgz", "image/jpg", "image/heic",
                "image/gif", "image/svg", "image/png", "image/bmp", "image/pjpeg", "image/avif"};
        byte count = 0;
        for (String acceptableContentType : acceptableContentTypes) {
            if (Objects.equals(file.getContentType(), acceptableContentType)) {
                count++;
            }
        }
        if (count == 0)
            throw new IllegalArgumentException("Недопустимый формат файла");
    }

}
