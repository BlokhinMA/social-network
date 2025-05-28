package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Photo;
import ru.sstu.socialnetworkbackend.entities.PhotoComment;

import java.util.List;

@Repository
public interface PhotoCommentRepository extends JpaRepository<PhotoComment, Long> {

    List<PhotoComment> findAllByPhotoOrderByCommentingTimeStampDesc(Photo photo);

    void deleteAllByPhotoId(Long photoId);

}
