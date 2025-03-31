package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {



}
