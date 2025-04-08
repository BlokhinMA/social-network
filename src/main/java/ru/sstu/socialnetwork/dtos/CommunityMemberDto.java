package ru.sstu.socialnetwork.dtos;

public class CommunityMemberDto {

    private Long userId;
    private Long communityId;

    public CommunityMemberDto() {
    }

    public CommunityMemberDto(Long userId, Long communityId) {
        this.userId = userId;
        this.communityId = communityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

}
