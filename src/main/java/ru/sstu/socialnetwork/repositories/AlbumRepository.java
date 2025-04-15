package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Album;
import ru.sstu.socialnetwork.entities.User;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByOwner(User owner);

    @Query(value = "SELECT * FROM albums WHERE title ILIKE %?1%", nativeQuery = true)
    List<Album> findAllLikeName(String keyword);

}
