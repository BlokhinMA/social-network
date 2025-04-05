package ru.sstu.socialnetwork.dtos;

public class CommunityMemberDto {

    private Long communityId;

    public CommunityMemberDto() {
    }

    public CommunityMemberDto(Long communityId) {
        this.communityId = communityId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

}
