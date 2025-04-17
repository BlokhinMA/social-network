package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Photo;
import ru.sstu.socialnetwork.entities.PhotoRating;

@Repository
public interface PhotoRatingRepository extends JpaRepository<PhotoRating, Long> {

    void deleteAllByPhoto(Photo photo);

    @Query(value = "SELECT AVG(rating::int) * 100 FROM photo_ratings WHERE photo_id = ?1", nativeQuery = true)
    Double ratingByPhotoId(Long photoId);

    @Query(value = "SELECT rating FROM photo_ratings WHERE rating_user_id = ?1 AND photo_id = ?2", nativeQuery = true)
    Boolean userRatingByRatingUserIdAndPhotoId(Long userId, Long photoId);

}
