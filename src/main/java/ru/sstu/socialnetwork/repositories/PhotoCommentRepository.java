package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Photo;
import ru.sstu.socialnetwork.entities.PhotoComment;

import java.util.List;

@Repository
public interface PhotoCommentRepository extends JpaRepository<PhotoComment, Long> {

    List<PhotoComment> findAllByPhotoOrderByCommentingTimeStampDesc(Photo photo);

    void deleteAllByPhoto(Photo photo);

}
