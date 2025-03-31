package ru.sstu.socialnetwork.dtos;

public class FriendshipDto {

    private Long firstUserId;
    private Long secondUserId;
    private Boolean accepted;

    public FriendshipDto() {
    }

    public FriendshipDto(Long firstUserId, Long secondUserId, Boolean accepted) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.accepted = accepted;
    }

    public Long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

}
