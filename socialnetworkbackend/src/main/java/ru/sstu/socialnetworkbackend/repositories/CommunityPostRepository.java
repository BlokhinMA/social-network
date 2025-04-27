package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.CommunityPost;

import java.util.List;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    List<CommunityPost> findAllByCommunityOrderByCreationTimeStampDesc(Community community);

    void deleteAllByCommunityId(Long communityId);

}
