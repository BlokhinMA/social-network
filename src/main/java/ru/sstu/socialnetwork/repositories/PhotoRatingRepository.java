package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.PhotoRating;

@Repository
public interface PhotoRatingRepository extends JpaRepository<PhotoRating, Long> {



}
