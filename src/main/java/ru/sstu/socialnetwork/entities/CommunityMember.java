package ru.sstu.socialnetwork.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "community_members", uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "community_id"})})
public class CommunityMember {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User member;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Community community;

    public CommunityMember() {
    }

    public CommunityMember(Long id, User member, Community community) {
        this.id = id;
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
