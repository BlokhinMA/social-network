package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Photo;
import ru.sstu.socialnetworkbackend.entities.PhotoTag;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoTagRepository extends JpaRepository<PhotoTag, Long> {

    Optional<PhotoTag> findByTagAndPhoto(String tag, Photo photo);

    List<PhotoTag> findAllByPhoto(Photo photo);

    void deleteAllByPhotoId(Long photoId);

}
