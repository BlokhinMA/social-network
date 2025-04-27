package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.User;

import java.util.List;

public class CommunitiesResponseDto {

    private User member;
    private List<Community> communities;

    public CommunitiesResponseDto() {
    }

    public CommunitiesResponseDto(User member, List<Community> communities) {
        this.member = member;
        this.communities = communities;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

}
