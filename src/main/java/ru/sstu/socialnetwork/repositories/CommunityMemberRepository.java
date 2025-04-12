package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.Community;
import ru.sstu.socialnetwork.entities.CommunityMember;
import ru.sstu.socialnetwork.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {

    Optional<CommunityMember> findByMemberAndCommunity(User member, Community community);

    List<CommunityMember> findAllByCommunity(Community community);

}
