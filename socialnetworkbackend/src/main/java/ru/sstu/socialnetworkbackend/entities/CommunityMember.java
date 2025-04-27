package ru.sstu.socialnetworkbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "community_members", uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "community_id"})})
public class CommunityMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User member;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Community community;

    public CommunityMember() {
    }

    public CommunityMember(User member, Community community) {
        this.member = member;
        this.community = community;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @Override
    public String toString() {
        return "CommunityMember{" +
                "id=" + id +
                ", member=" + member +
                ", community=" + community +
                '}';
    }

}
