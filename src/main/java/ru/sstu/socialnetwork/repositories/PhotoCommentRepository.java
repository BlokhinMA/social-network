package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Photo;
import ru.sstu.socialnetwork.entities.PhotoComment;

@Repository
public interface PhotoCommentRepository extends JpaRepository<PhotoComment, Long> {

    void deleteAllByPhoto(Photo photo);

}
