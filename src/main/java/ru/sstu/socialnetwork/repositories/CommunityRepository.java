package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Community;
import ru.sstu.socialnetwork.entities.User;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findAllByCreator(User creator);

    @Query(value = "SELECT c.* FROM communities c JOIN community_members m ON c.id = community_id WHERE member_id = ?1", nativeQuery = true)
    List<Community> findAllByMemberId(Long memberId);

    @Query(value = "SELECT * FROM communities WHERE name LIKE ?1", nativeQuery = true)
    List<Community> findAllLikeName(String name);

}
