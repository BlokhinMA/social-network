package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.dtos.AlbumDto;
import ru.sstu.socialnetwork.dtos.PhotoDto;
import ru.sstu.socialnetwork.entities.Album;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.entities.enums.AccessType;
import ru.sstu.socialnetwork.exceptions.IncorrectRequestValuesException;
import ru.sstu.socialnetwork.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetwork.repositories.AlbumRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
public class AlbumService {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(AlbumService.class);
    private final AlbumRepository albumRepository;
    private final PhotoService photoService;
    private final FriendshipService friendshipService;
    private final UserService userService;
    private final String accessDeniedMessage = "У Вас недостаточно прав на выполнение данной операции";

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
        if (albumDto.getFiles() != null) {
            PhotoDto photoDto = new PhotoDto(albumDto.getFiles(), createdAlbum.getId());
            photoService.create(photoDto, principal);
        }
        return createdAlbum;
    }

    public List<Album> showAll(Principal principal) {
        User owner = userService.getCurrentUser(principal);
        return albumRepository.findAllByOwner(owner);
    }

    public List<Album> showAll(Long ownerId) {
        User owner = userService.getUserById(ownerId);
        return albumRepository.findAllByOwner(owner);
    }

    public Album show(Long id, Principal principal) {
        Album album = getAlbumFromDB(id);
        if (album.getAccessType() == AccessType.FRIENDS && !friendshipService.isFriend(album.getOwner().getId(), principal)) {
            throw new AccessDeniedException(accessDeniedMessage);
        }
        return album;
    }

    public Album delete(Long id, Principal principal) {
        Album album = getAlbumFromDB(id);
        User currentUser = userService.getCurrentUser(principal);
        User owner = album.getOwner();
        if (!Objects.equals(currentUser, owner)) {
            throw new AccessDeniedException(accessDeniedMessage);
        }
        albumRepository.deleteById(album.getId());
        log.info("Пользователь {} удалил альбом {}",
                currentUser,
                album);
        return album;
    }

    public List<Album> find(String word) {
        if (word.isEmpty())
            throw new IncorrectRequestValuesException("Некорректное ключевое слово");
        return albumRepository.findAllLikeName(word);
    }
//
//    public boolean changeAccessType(Album album, Principal principal) {
//        if (albumRepository.findById(album.getId()) == null)
//            return false;
//        Album updatedAlbum = albumRepository.updateAccessTypeById(album);
//        log.info("Пользователь {} обновил альбом {}",
//                userRepository.findByLogin(principal.getName()),
//                updatedAlbum);
//        return true;
//    }
//
//    public boolean isFriend(Principal principal, String userLogin) {
//        return friendshipRepository.findByFriendLoginAndUserLogin(principal.getName(), userLogin) != null;
//    }
//
//    public List<Album> showAll() {
//        return albumRepository.findAll();
//    }

    private Album getAlbumFromDB(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Альбома не существует"));
    }

}
