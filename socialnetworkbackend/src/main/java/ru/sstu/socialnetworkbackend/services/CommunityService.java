package ru.sstu.socialnetworkbackend.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.communities.*;
import ru.sstu.socialnetworkbackend.entities.Community;
import ru.sstu.socialnetworkbackend.entities.CommunityMember;
import ru.sstu.socialnetworkbackend.entities.CommunityPost;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.exceptions.IncorrectKeywordException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceAlreadyExistsException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.repositories.CommunityMemberRepository;
import ru.sstu.socialnetworkbackend.repositories.CommunityPostRepository;
import ru.sstu.socialnetworkbackend.repositories.CommunityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService extends SuperService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(CommunityService.class);

    public CommunityService(CommunityRepository communityRepository,
                            CommunityMemberRepository communityMemberRepository,
                            CommunityPostRepository communityPostRepository,
                            UserService userService) {
        super();
        this.communityRepository = communityRepository;
        this.communityMemberRepository = communityMemberRepository;
        this.communityPostRepository = communityPostRepository;
        this.userService = userService;
    }

    public List<Community> showAllOwn() {
        User creator = userService.getCurrentUser();
        return communityRepository.findAllByCreator(creator);
    }

    public List<Community> showAll() {
        User member = userService.getCurrentUser();
        return communityRepository.findAllByMemberId(member.getId());
    }

    public CommunitiesResponseDto showAll(Long memberId) {
        User member = userService.getUserById(memberId);
        return new CommunitiesResponseDto(
            member,
            communityRepository.findAllByMemberId(member.getId())
        );
    }

    public CommunityResponseDto show(Long id) {
        User currentUser = userService.getCurrentUser();
        Community community = getCommunityFromDB(id);
        List<CommunityMember> members = communityMemberRepository.findAllByCommunityOrderByIdDesc(community);
        List<CommunityPost> postsFromDB = communityPostRepository.findAllByCommunityOrderByCreationTimeStampDesc(community);
        List<CommunityPostResponseDto> posts = getPostResponseDtos(postsFromDB, currentUser);
        Boolean isMember = communityMemberRepository.findByMemberAndCommunity(currentUser, community).isPresent();
        Boolean isCreator = currentUser.equals(community.getCreator());
        return new CommunityResponseDto(
            community,
            members,
            posts,
            isMember,
            isCreator
        );
    }

    private static List<CommunityPostResponseDto> getPostResponseDtos(List<CommunityPost> postsFromDB, User currentUser) {
        List<CommunityPostResponseDto> posts = new ArrayList<>();
        for (CommunityPost postFromDB : postsFromDB) {
            CommunityPostResponseDto post = new CommunityPostResponseDto(
                postFromDB.getId(),
                postFromDB.getPostText(),
                postFromDB.getCreationTimeStamp(),
                postFromDB.getAuthor(),
                postFromDB.getCommunity(),
                currentUser.equals(postFromDB.getAuthor())
            );
            posts.add(post);
        }
        return posts;
    }

    public Community create(CommunityDto dto) {
        User currentUser = userService.getCurrentUser();
        Community community = new Community(
            dto.name(),
            currentUser
        );
        Community createdCommunity = communityRepository.save(community);
        LOG.info("Пользователь {} добавил сообщество {}",
            currentUser,
            createdCommunity);
        return createdCommunity;
    }

    @Transactional
    public Community delete(Long id) {
        User currentUser = userService.getCurrentUser();
        Community community = getCommunityFromDB(id);
        checkRights(currentUser, community.getCreator());
        communityMemberRepository.deleteAllByCommunityId(community.getId());
        communityPostRepository.deleteAllByCommunityId(community.getId());
        communityRepository.deleteById(id);
        LOG.info("Пользователь {} удалил сообщество {}",
            currentUser,
            community);
        return community;
    }

    public CommunityMember join(Long communityId) {
        User currentUser = userService.getCurrentUser();
        Community community = getCommunityFromDB(communityId);
        if (communityMemberRepository.findByMemberAndCommunity(currentUser, community).isPresent())
            throw new ResourceAlreadyExistsException("Вы уже являетесь участником сообщества");
        CommunityMember member = new CommunityMember(
            currentUser,
            community
        );
        CommunityMember joinedMember = communityMemberRepository.save(member);
        LOG.info("Пользователь {} стал участником сообщества {}",
            currentUser,
            joinedMember);
        return joinedMember;
    }

    public CommunityMember leave(Long communityId) {
        User currentUser = userService.getCurrentUser();
        Community community = getCommunityFromDB(communityId);
        CommunityMember communityMember = communityMemberRepository.findByMemberAndCommunity(currentUser, community)
            .orElseThrow(() -> new ResourceNotFoundException("Вы не являетесь участником сообщества"));
        communityMemberRepository.deleteById(communityMember.getId());
        LOG.info("Пользователь {} перестал быть участником сообщества {}",
            currentUser,
            communityMember);
        return communityMember;
    }

    public CommunityMember kick(Long id) {
        User currentUser = userService.getCurrentUser();
        CommunityMember member = communityMemberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Данный пользователь не является участником " +
                "сообщества или этого пользователя или сообщества не существует"));
        checkRights(currentUser, member.getCommunity().getCreator());
        communityMemberRepository.deleteById(id);
        LOG.info("Пользователь {} выгнал участника сообщества {}",
            currentUser,
            member);
        return member;
    }

    public CommunityPost createPost(CommunityPostDto dto) {
        User currentUser = userService.getCurrentUser();
        Community community = getCommunityFromDB(dto.communityId());
        checkRights(communityMemberRepository.findByMemberAndCommunity(currentUser, community).isPresent(), currentUser,
            community.getCreator());
        CommunityPost post = new CommunityPost(
            dto.postText(),
            currentUser,
            community
        );
        CommunityPost createdPost = communityPostRepository.save(post);
        LOG.info("Пользователь {} добавил пост {}",
            currentUser,
            createdPost);
        return createdPost;
    }

    public CommunityPost deletePost(Long id) {
        CommunityPost post = communityPostRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Пост сообщества не найден или его не существует"));
        User currentUser = userService.getCurrentUser();
        checkRights(currentUser, post.getAuthor(), post.getCommunity().getCreator());
        communityPostRepository.deleteById(id);
        LOG.info("Пользователь {} удалил пост {}",
            currentUser,
            post);
        return post;
    }

    public List<Community> find(String keyword) {
        if (keyword.isEmpty())
            throw new IncorrectKeywordException();
        return communityRepository.findAllILikeName(keyword);
    }

    private Community getCommunityFromDB(Long id) {
        return communityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Сообщество не найдено"));
    }

}
