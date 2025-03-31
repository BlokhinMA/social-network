package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.PhotoTag;

@Repository
public interface PhotoTagRepository extends JpaRepository<PhotoTag, Long> {



}
