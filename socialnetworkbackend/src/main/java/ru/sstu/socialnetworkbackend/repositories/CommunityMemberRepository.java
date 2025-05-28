package ru.sstu.socialnetworkbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.CommunityMember;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {

    Optional<CommunityMember> findByMemberAndCommunity(User member, Community community);

    List<CommunityMember> findAllByCommunityOrderByIdDesc(Community community);

    void deleteAllByCommunityId(Long communityId);

}
