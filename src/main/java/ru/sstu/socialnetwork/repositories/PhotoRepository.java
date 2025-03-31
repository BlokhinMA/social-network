package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {



}
