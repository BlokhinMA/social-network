package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Photo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query(value = "SELECT id FROM photos WHERE album_id = ?1", nativeQuery = true)
    List<Long> findAllIdByAlbumId(Long albumId);

    @Query(value = "SELECT id FROM photos WHERE creation_time_stamp = ?1", nativeQuery = true)
    List<Long> findAllIdByCreationTimeStamp(LocalDateTime creationTimeStamp);

    @Query(value = "SELECT p.id FROM photos p JOIN photo_tags ON p.id = photo_id WHERE tag ILIKE %?1%", nativeQuery = true)
    List<Long> findAllIdLikeTag(String keyword);

    @Query(value = "SELECT p.id FROM photos p JOIN photo_comments ON p.id = photo_id WHERE comment ILIKE %?1%", nativeQuery = true)
    List<Long> findAllIdLikeComment(String keyword);

}
