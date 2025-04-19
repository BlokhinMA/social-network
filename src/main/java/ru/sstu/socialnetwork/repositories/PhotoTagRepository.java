package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Photo;
import ru.sstu.socialnetwork.entities.PhotoTag;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoTagRepository extends JpaRepository<PhotoTag, Long> {

    void deleteAllByPhoto(Photo photo);

    List<PhotoTag> findAllByPhoto(Photo photo);

    Optional<PhotoTag> findByTagAndPhoto(String tag, Photo photo);

}
