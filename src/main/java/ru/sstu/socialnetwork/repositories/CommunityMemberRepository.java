package ru.sstu.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sstu.socialnetwork.entities.CommunityMember;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {



}
