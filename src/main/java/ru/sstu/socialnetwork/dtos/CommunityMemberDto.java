package ru.sstu.socialnetwork.dtos;

public class CommunityMemberDto {

    private Long memberId;
    private Long communityId;

    public CommunityMemberDto() {
    }

    public CommunityMemberDto(Long memberId, Long communityId) {
        this.memberId = memberId;
        this.communityId = communityId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

}
