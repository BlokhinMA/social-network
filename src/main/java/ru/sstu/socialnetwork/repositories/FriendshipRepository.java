package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {



}
