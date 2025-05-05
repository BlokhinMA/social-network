package ru.sstu.socialnetworkbackend.services;

import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.socialnetworkbackend.dtos.*;
import ru.sstu.socialnetworkbackend.entities.Album;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.entities.enums.AccessType;
import ru.sstu.socialnetworkbackend.exceptions.IncorrectKeywordException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.repositories.AlbumRepository;

import java.security.Principal;
import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoService photoService;
    private final FriendshipService friendshipService;
    private final UserService userService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AlbumService.class);

    public AlbumService(AlbumRepository albumRepository,
                        PhotoService photoService,
                        FriendshipService friendshipService,
                        UserService userService) {
        this.albumRepository = albumRepository;
        this.photoService = photoService;
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    public Album create(AlbumDto albumDto, Principal principal) {
        User owner = userService.getCurrentUser(principal);
        Album album = new Album(
                albumDto.getTitle(),
                AccessType.valueOf(albumDto.getAccessType()),
                owner
        );
        Album createdAlbum = albumRepository.save(album);
        log.info("Пользователь {} добавил альбом {}",
                owner,
                createdAlbum);
        List<MultipartFile> files = albumDto.getFiles();
        if (files != null && files.getFirst().getSize() != 0) {
            PhotoDto photoDto = new PhotoDto(albumDto.getFiles(), createdAlbum.getId());
            photoService.create(photoDto, principal);
        }
        return createdAlbum;
    }

    public List<Album> showAll(Principal principal) {
        User owner = userService.getCurrentUser(principal);
        return albumRepository.findAllByOwnerOrderByIdDesc(owner);
    }

    public AlbumsResponseDto showAll(Long ownerId) {
        User owner = userService.getUserById(ownerId);
        List<Album> albums = albumRepository.findAllByOwnerOrderByIdDesc(owner);
        return new AlbumsResponseDto(
                owner,
                albums
        );
    }

    public AlbumResponseDto show(Long id, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Album album = getAlbumFromDB(id);
        if (!currentUser.equals(album.getOwner()) &&
                album.getAccessType() == AccessType.FRIENDS &&
                !friendshipService.isFriend(album.getOwner().getId(), principal)) {
            throw new AccessDeniedException("Этот альбом доступен только для друзей");
        }
        List<Long> photos = photoService.showAll(album.getId());
        return new AlbumResponseDto(
                album,
                photos,
                currentUser.equals(album.getOwner())
        );
    }

    @Transactional
    public Album delete(Long id, Principal principal) {
        Album album = getAlbumFromDB(id);
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        checkRights(!currentUser.equals(owner));
        photoService.deleteAllByAlbumId(album.getId(), principal);
        albumRepository.deleteById(album.getId());
        log.info("Пользователь {} удалил альбом {}",
                currentUser,
                album);
        return album;
    }

    public List<Album> find(String keyword) {
        if (keyword.isEmpty())
            throw new IncorrectKeywordException();
        return albumRepository.findAllILikeName(keyword);
    }

    public Album changeAccessType(ChangeAlbumAccessTypeDto dto, Principal principal) {
        Album album = getAlbumFromDB(dto.getId());
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        checkRights(!currentUser.equals(owner));
        album.setAccessType(AccessType.valueOf(dto.getAccessType()));
        Album updatedAlbum = albumRepository.save(album);
        log.info("Пользователь {} обновил альбом {}",
                currentUser,
                updatedAlbum);
        return updatedAlbum;
    }

    private Album getAlbumFromDB(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
    }

    private void checkRights(boolean condition) {
        if (condition) {
            throw new AccessDeniedException("У Вас недостаточно прав на выполнение данной операции");
        }
    }

}
