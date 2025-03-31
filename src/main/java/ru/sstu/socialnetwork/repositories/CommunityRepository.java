package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {



}
