package ru.sstu.socialnetworkbackend.dtos.communities;

import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.CommunityMember;

import java.util.List;

public record CommunityResponseDto(
    Community community,
    List<CommunityMember> members,
    List<CommunityPostResponseDto> posts,
    Boolean isMember,
    Boolean isCreator
) {

}
