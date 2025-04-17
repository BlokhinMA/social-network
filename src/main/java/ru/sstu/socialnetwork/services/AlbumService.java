package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.dtos.AlbumDto;
import ru.sstu.socialnetwork.dtos.AlbumResponseDto;
import ru.sstu.socialnetwork.dtos.ChangeAlbumAccessTypeDto;
import ru.sstu.socialnetwork.dtos.PhotoDto;
import ru.sstu.socialnetwork.entities.Album;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.entities.enums.AccessType;
import ru.sstu.socialnetwork.exceptions.IncorrectKeywordException;
import ru.sstu.socialnetwork.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetwork.repositories.AlbumRepository;

import java.security.Principal;
import java.util.List;

@Service
public class AlbumService {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(AlbumService.class);
    private final AlbumRepository albumRepository;
    private final PhotoService photoService;
    private final FriendshipService friendshipService;
    private final UserService userService;

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
        Album album = new Album();
        album.setTitle(albumDto.getTitle());
        album.setAccessType(AccessType.valueOf(albumDto.getAccessType()));
        album.setOwner(owner);
        Album createdAlbum = albumRepository.save(album);
        log.info("Пользователь {} добавил альбом {}",
                owner,
                createdAlbum);
        if (albumDto.getFiles().getFirst().getSize() != 0) {
            PhotoDto photoDto = new PhotoDto(albumDto.getFiles(), createdAlbum.getId());
            photoService.create(photoDto, principal);
        }
        return createdAlbum;
    }

    public List<Album> showAll(Principal principal) {
        User owner = userService.getCurrentUser(principal);
        return albumRepository.findAllByOwnerOrderByIdDesc(owner);
    }

    public List<Album> showAll(Long ownerId) {
        User owner = userService.getUser(ownerId);
        return albumRepository.findAllByOwnerOrderByIdDesc(owner);
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

    public Album delete(Long id, Principal principal) {
        Album album = getAlbumFromDB(id);
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        checkRights(!currentUser.equals(owner));
        photoService.deleteAllByAlbum(album, principal);
        albumRepository.deleteById(album.getId());
        log.info("Пользователь {} удалил альбом {}",
                currentUser,
                album);
        return album;
    }

    public List<Album> find(String keyword) {
        if (keyword.isEmpty())
            throw new IncorrectKeywordException();
        return albumRepository.findAllLikeName(keyword);
    }

    public boolean changeAccessType(ChangeAlbumAccessTypeDto dto, Principal principal) {
        Album album = getAlbumFromDB(dto.getId());
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        checkRights(!currentUser.equals(owner));
        album.setAccessType(AccessType.valueOf(dto.getAccessType()));
        Album updatedAlbum = albumRepository.save(album);
        log.info("Пользователь {} обновил альбом {}",
                currentUser,
                updatedAlbum);
        return true;
    }

    public List<Album> showAll() {
        return albumRepository.findAll();
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
