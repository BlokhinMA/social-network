package ru.sstu.socialnetworkbackend.dtos;

import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.CommunityMember;

import java.util.List;

public class CommunityResponseDto {

    private Community community;
    private List<CommunityMember> members;
    private List<CommunityPostResponseDto> posts;
    private Boolean isMember;
    private Boolean isCreator;

    public CommunityResponseDto() {
    }

    public CommunityResponseDto(Community community, List<CommunityMember> members, List<CommunityPostResponseDto> posts, Boolean isMember, Boolean isCreator) {
        this.community = community;
        this.members = members;
        this.posts = posts;
        this.isMember = isMember;
        this.isCreator = isCreator;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public List<CommunityMember> getMembers() {
        return members;
    }

    public void setMembers(List<CommunityMember> members) {
        this.members = members;
    }

    public List<CommunityPostResponseDto> getPosts() {
        return posts;
    }

    public void setPosts(List<CommunityPostResponseDto> posts) {
        this.posts = posts;
    }

    public Boolean isMember() {
        return isMember;
    }

    public void setMember(Boolean member) {
        isMember = member;
    }

    public Boolean isCreator() {
        return isCreator;
    }

    public void setCreator(Boolean creator) {
        isCreator = creator;
    }

}
