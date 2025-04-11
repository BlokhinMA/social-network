package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import ru.sstu.socialnetwork.repositories.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.Principal;

public class AlbumService {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(AlbumService.class);
    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public AlbumService(AlbumRepository albumRepository, PhotoRepository photoRepository, PhotoTagRepository photoTagRepository, PhotoRatingRepository photoRatingRepository, PhotoCommentRepository photoCommentRepository, FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.photoTagRepository = photoTagRepository;
        this.photoRatingRepository = photoRatingRepository;
        this.photoCommentRepository = photoCommentRepository;
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

//    public void create(Album album, List<MultipartFile> files, Principal principal) throws IOException {
//        if (principal == null)
//            throw new RuntimeException();
//
//        album.setUserLogin(principal.getName());
//        Album createdAlbum = albumRepository.save(album);
//        log.info("Пользователь {} добавил альбом {}",
//                userRepository.findByLogin(principal.getName()),
//                createdAlbum);
//        createPhotos(files, createdAlbum.getId(), principal);
//    }
//
//    private Photo toPhotoEntity(MultipartFile file) throws IOException {
//        Photo photo = new Photo();
//        photo.setName(file.getName());
//        photo.setOriginalFileName(file.getOriginalFilename());
//        photo.setSize(file.getSize());
//        photo.setContentType(file.getContentType());
//        photo.setBytes(file.getBytes());
//        return photo;
//    }
//
//    public List<Album> showAll(Principal principal) {
//        return albumRepository.findAllByUserLogin(principal.getName());
//    }
//
//    public List<Album> showAll(String userLogin) {
//        return albumRepository.findAllByUserLogin(userLogin);
//    }
//
//    public Album show(Long id) {
//        Album album = albumRepository.findById(id);
//        album.setPhotos(photoRepository.findAllByAlbumId(id));
//        return album;
//    }
//
//    public void delete(Long id, Principal principal) {
//        List<Photo> photos = photoRepository.findAllByAlbumId(id);
//        for (Photo photo : photos) {
//            Long photoId = photo.getId();
//            photo.setTags(photoTagRepository.findAllByPhotoId(photoId));
//            photo.setRatings(photoRatingRepository.findAllByPhotoId(photoId));
//            photo.setComments(photoCommentRepository.findAllByPhotoId(photoId));
//        }
//        Album deletedAlbum = albumRepository.deleteById(id);
//        deletedAlbum.setPhotos(photos);
//        log.info("Пользователь {} удалил альбом {}",
//                userRepository.findByLogin(principal.getName()),
//                deletedAlbum);
//    }
//
//    public boolean createPhotos(List<MultipartFile> files, Long albumId, Principal principal) throws IOException {
//        if (Objects.requireNonNull(files.get(0).getOriginalFilename()).isEmpty())
//            return false;
//        List<Photo> photos = new ArrayList<>();
//        List<Photo> createdPhotos = new ArrayList<>();
//        for (int i = 0; i < files.size(); i++) {
//            photos.add(toPhotoEntity(files.get(i)));
//            photos.get(i).setAlbumId(albumId);
//            Photo createdPhoto = photoRepository.save(photos.get(i));
//            createdPhoto.setAlbum(albumRepository.findById(createdPhoto.getAlbumId()));
//            createdPhotos.add(createdPhoto);
//        }
//        log.info("Пользователь {} добавил фотографии {}",
//                userRepository.findByLogin(principal.getName()),
//                createdPhotos);
//        return true;
//    }
//
//    public List<Album> find(String word) {
//        if (word != null && !word.isEmpty())
//            return albumRepository.findAllLikeName(word);
//        return null;
//    }
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


}
