package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Photo;
import ru.sstu.socialnetwork.entities.PhotoTag;

import java.util.List;

@Repository
public interface PhotoTagRepository extends JpaRepository<PhotoTag, Long> {

    void deleteAllByPhoto(Photo photo);

    List<PhotoTag> findAllByPhoto(Photo photo);

}
