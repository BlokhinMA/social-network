package ru.sstu.socialnetworkbackend.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sstu.socialnetworkbackend.dtos.albums.AlbumDto;
import ru.sstu.socialnetworkbackend.dtos.albums.AlbumResponseDto;
import ru.sstu.socialnetworkbackend.dtos.albums.AlbumsResponseDto;
import ru.sstu.socialnetworkbackend.dtos.albums.ChangeAlbumAccessTypeDto;
import ru.sstu.socialnetworkbackend.dtos.photos.PhotoDto;
import ru.sstu.socialnetworkbackend.entities.Album;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.entities.enums.AccessType;
import ru.sstu.socialnetworkbackend.exceptions.IncorrectKeywordException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.repositories.AlbumRepository;

import java.util.List;

@Service
public class AlbumService extends SuperService {

    private final AlbumRepository albumRepository;
    private final PhotoService photoService;
    private final FriendshipService friendshipService;
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(AlbumService.class);

    public AlbumService(AlbumRepository albumRepository,
                        PhotoService photoService,
                        FriendshipService friendshipService,
                        UserService userService) {
        this.albumRepository = albumRepository;
        this.photoService = photoService;
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    public Album create(AlbumDto albumDto) {
        User owner = userService.getCurrentUser();
        Album album = new Album(
                albumDto.title(),
                AccessType.valueOf(albumDto.accessType()),
                owner
        );
        Album createdAlbum = albumRepository.save(album);
        LOG.info("Пользователь {} добавил альбом {}",
                owner,
                createdAlbum);
        List<MultipartFile> files = albumDto.files();
        if (files != null && files.getFirst().getSize() != 0) {
            PhotoDto photoDto = new PhotoDto(albumDto.files(), createdAlbum.getId());
            photoService.create(photoDto);
        }
        return createdAlbum;
    }

    public List<Album> showAll() {
        User owner = userService.getCurrentUser();
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

    public AlbumResponseDto show(Long id) {
        User currentUser = userService.getCurrentUser();
        Album album = getAlbumFromDB(id);
        User owner = album.getOwner();
        if (!currentUser.equals(owner) &&
                album.getAccessType() == AccessType.FRIENDS &&
                !friendshipService.areFriends(currentUser.getId(), owner.getId()))
            throw new AccessDeniedException("Этот альбом доступен только для друзей");
        List<Long> photos = photoService.showAll(album.getId());
        return new AlbumResponseDto(
                album,
                photos,
                currentUser.equals(album.getOwner())
        );
    }

    @Transactional
    public Album delete(Long id) {
        Album album = getAlbumFromDB(id);
        User currentUser = userService.getCurrentUser();
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        photoService.deleteAllByAlbumId(album.getId());
        albumRepository.deleteById(album.getId());
        LOG.info("Пользователь {} удалил альбом {}",
                currentUser,
                album);
        return album;
    }

    public List<Album> find(String keyword) {
        if (keyword.isEmpty())
            throw new IncorrectKeywordException();
        return albumRepository.findAllILikeName(keyword);
    }

    public Album changeAccessType(ChangeAlbumAccessTypeDto dto) {
        Album album = getAlbumFromDB(dto.id());
        User currentUser = userService.getCurrentUser();
        User owner = album.getOwner();
        checkRights(currentUser, owner);
        album.setAccessType(AccessType.valueOf(dto.accessType()));
        Album updatedAlbum = albumRepository.save(album);
        LOG.info("Пользователь {} обновил альбом {}",
                currentUser,
                updatedAlbum);
        return updatedAlbum;
    }

    private Album getAlbumFromDB(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
    }

}
