package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.dtos.CommunityDto;
import ru.sstu.socialnetwork.dtos.CommunityPostDto;
import ru.sstu.socialnetwork.dtos.CommunityPostResponseDto;
import ru.sstu.socialnetwork.dtos.CommunityResponseDto;
import ru.sstu.socialnetwork.entities.Community;
import ru.sstu.socialnetwork.entities.CommunityMember;
import ru.sstu.socialnetwork.entities.CommunityPost;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.exceptions.IncorrectKeywordException;
import ru.sstu.socialnetwork.exceptions.ResourceAlreadyExistsException;
import ru.sstu.socialnetwork.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetwork.repositories.CommunityMemberRepository;
import ru.sstu.socialnetwork.repositories.CommunityPostRepository;
import ru.sstu.socialnetwork.repositories.CommunityRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(CommunityService.class);
    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserService userService;

    public CommunityService(CommunityRepository communityRepository,
                            CommunityMemberRepository communityMemberRepository,
                            CommunityPostRepository communityPostRepository,
                            UserService userService) {
        this.communityRepository = communityRepository;
        this.communityMemberRepository = communityMemberRepository;
        this.communityPostRepository = communityPostRepository;
        this.userService = userService;
    }

    public List<Community> showAllOwn(Principal principal) {
        User creator = userService.getCurrentUser(principal);
        return communityRepository.findAllByCreator(creator);
    }

    public List<Community> showAll(Principal principal) {
        User member = userService.getCurrentUser(principal);
        return communityRepository.findAllByMemberId(member.getId());
    }

    public List<Community> showAll(Long memberId) {
        User member = userService.getUser(memberId);
        return communityRepository.findAllByMemberId(member.getId());
    }

    public CommunityResponseDto show(Long id, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(id);
        List<CommunityMember> members = communityMemberRepository.findAllByCommunity(community);
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

    public Community create(CommunityDto dto, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Community community = new Community();
        community.setName(dto.getName());
        community.setCreator(currentUser);
        Community createdCommunity = communityRepository.save(community);
        log.info("Пользователь {} добавил сообщество {}",
                currentUser,
                createdCommunity);
        return createdCommunity;
    }

    public Community delete(Long id, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(id);
        checkRights(!currentUser.equals(community.getCreator()));
        if (!communityMemberRepository.findAllByCommunity(community).isEmpty()) {
            communityMemberRepository.deleteAllByCommunity(community);
        }
        if (!communityPostRepository.findAllByCommunity(community).isEmpty()) {
            communityPostRepository.deleteAllByCommunity(community);
        }
        communityRepository.deleteById(id);
        log.info("Пользователь {} удалил сообщество {}",
                currentUser,
                community);
        return community;
    }

    public CommunityMember join(Long communityId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(communityId);
        if (communityMemberRepository.findByMemberAndCommunity(currentUser, community).isPresent())
            throw new ResourceAlreadyExistsException("Вы уже являетесь участником сообщества");
        CommunityMember member = new CommunityMember();
        member.setMember(currentUser);
        member.setCommunity(community);
        CommunityMember joinedMember = communityMemberRepository.save(member);
        log.info("Пользователь {} стал участником сообщества {}",
                currentUser,
                joinedMember);
        return joinedMember;
    }

    public CommunityMember leave(Long communityId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(communityId);
        CommunityMember communityMember = communityMemberRepository.findByMemberAndCommunity(currentUser, community)
                .orElseThrow(() -> new ResourceNotFoundException("Вы не являетесь участником сообщества"));
        communityMemberRepository.deleteById(communityMember.getId());
        log.info("Пользователь {} перестал быть участником сообщества {}",
                currentUser,
                communityMember);
        return communityMember;
    }

    public CommunityMember kick(Long id, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        CommunityMember member = communityMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Данный пользователь не является участником " +
                        "сообщества или этого пользователя или сообщества не существует"));
        checkRights(!currentUser.equals(member.getCommunity().getCreator()));
        communityMemberRepository.deleteById(id);
        log.info("Пользователь {} выгнал участника сообщества {}",
                currentUser,
                member);
        return member;
    }

    public CommunityPost createPost(CommunityPostDto dto, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(dto.getCommunityId());
        checkRights(communityMemberRepository.findByMemberAndCommunity(currentUser, community).isEmpty() &&
                !currentUser.equals(community.getCreator()));
        CommunityPost post = new CommunityPost();
        post.setPostText(dto.getPostText());
        post.setAuthor(currentUser);
        post.setCommunity(community);
        CommunityPost createdPost = communityPostRepository.save(post);
        log.info("Пользователь {} добавил пост {}",
                currentUser,
                createdPost);
        return createdPost;
    }

    public CommunityPost deletePost(Long id, Principal principal) {
        CommunityPost post = communityPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пост сообщества не найден или его не существует"));
        User currentUser = userService.getCurrentUser(principal);
        checkRights(!currentUser.equals(post.getAuthor()));
        communityPostRepository.deleteById(id);
        log.info("Пользователь {} удалил пост {}",
                currentUser,
                post);
        return post;
    }

    public List<Community> find(String keyword) {
        if (keyword.isEmpty())
            throw new IncorrectKeywordException();
        return communityRepository.findAllLikeName(keyword);
    }



    public List<Community> showAll() {
        return communityRepository.findAll();
    }

    public List<CommunityMember> showAllMembers() {
        return communityMemberRepository.findAll();
    }

    public List<CommunityPost> showAllPosts() {
        return communityPostRepository.findAll();
    }



    private Community getCommunityFromDB(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сообщество не найдено"));
    }

    private void checkRights(boolean condition) {
        if (condition)
            throw new AccessDeniedException("У Вас недостаточно прав на выполнение данной операции");
    }

}
