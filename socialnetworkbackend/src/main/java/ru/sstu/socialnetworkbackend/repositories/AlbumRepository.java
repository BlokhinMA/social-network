package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Album;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByOwnerOrderByIdDesc(User owner);

    @Query(value = "SELECT * FROM albums WHERE title ILIKE %?1%", nativeQuery = true)
    List<Album> findAllILikeName(String keyword);

}
