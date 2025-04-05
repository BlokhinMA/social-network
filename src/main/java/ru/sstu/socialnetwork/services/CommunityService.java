package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.dtos.CommunityDto;
import ru.sstu.socialnetwork.dtos.CommunityPostDto;
import ru.sstu.socialnetwork.entities.Community;
import ru.sstu.socialnetwork.entities.CommunityMember;
import ru.sstu.socialnetwork.entities.CommunityPost;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.exceptions.ResourceAlreadyExistsException;
import ru.sstu.socialnetwork.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetwork.repositories.CommunityMemberRepository;
import ru.sstu.socialnetwork.repositories.CommunityPostRepository;
import ru.sstu.socialnetwork.repositories.CommunityRepository;

import java.security.Principal;
import java.util.List;

@Service
public class CommunityService {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(CommunityService.class);
    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserService userService;
    private final String accessDeniedMessage = "У Вас недостаточно прав на выполнение данной операции";

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

    public List<Community> showAll(String memberLogin) {
        User member = userService.getUserByLogin(memberLogin);
        return communityRepository.findAllByMemberId(member.getId());
    }

    public Community show(Long id) {
        return getCommunityFromDB(id);
    }

    public List<CommunityMember> showAllMembers(Long id) {
        Community community = getCommunityFromDB(id);
        return communityMemberRepository.findAllByCommunity(community);
    }

    public List<CommunityPost> showAllPosts(Long id) {
        Community community = getCommunityFromDB(id);
        return communityPostRepository.findAllByCommunity(community);
    }

    public Community create(CommunityDto communityDto, Principal principal) {
        User creator = userService.getCurrentUser(principal);
        Community community = new Community(communityDto.getName(), creator);
        Community createdCommunity = communityRepository.save(community);
        log.info("Пользователь {} добавил сообщество {}",
                creator,
                createdCommunity);
        return createdCommunity;
    }

    public Community delete(Long id, Principal principal) {
        User creator = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(id);
        checkAuthorities(id, creator);
        communityRepository.deleteById(id);
        log.info("Пользователь {} удалил сообщество {}",
                creator,
                community);
        return community;
    }

    public CommunityMember join(Principal principal, Long communityId) {
        User member = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(communityId);
        if (isMember(principal, communityId))
            throw new ResourceAlreadyExistsException("Вы уже являетесь участником сообщества");
        CommunityMember communityMember = new CommunityMember(member, community);
        CommunityMember joinedCommunityMember = communityMemberRepository.save(communityMember);
        log.info("Пользователь {} стал участником сообщества {}",
                member,
                joinedCommunityMember);
        return joinedCommunityMember;
    }

    public boolean isMember(Principal principal, Long communityId) {
        User user = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(communityId);
        return communityMemberRepository.findByMemberAndCommunity(user, community).isPresent();
    }

    public CommunityMember leave(Principal principal, Long communityId) {
        User member = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(communityId);
        CommunityMember communityMember = communityMemberRepository.findByMemberAndCommunity(member, community)
                .orElseThrow(() -> new ResourceNotFoundException("Вы не являетесь участником сообщества"));
        communityMemberRepository.deleteById(communityMember.getId());
        log.info("Пользователь {} перестал быть участником сообщества {}",
                member,
                communityMember);
        return communityMember;
    }

    public CommunityMember kick(Long id, Principal principal) {
        User creator = userService.getCurrentUser(principal);
        CommunityMember communityMember = communityMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Данный пользователь не является участником " +
                        "сообщества или этого пользователя или сообщества не существует"));
        checkAuthorities(communityMember.getCommunity().getId(), creator);
        communityPostRepository.deleteById(id);
        log.info("Пользователь {} выгнал участника сообщества {}",
                creator,
                communityMember);
        return communityMember;
    }

    public CommunityPost createPost(CommunityPostDto communityPostDto, Principal principal) {
        User author = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(communityPostDto.getCommunityId());
        if (!isMember(principal, communityPostDto.getCommunityId()))
            throw new AccessDeniedException(accessDeniedMessage);
        CommunityPost communityPost = new CommunityPost(communityPostDto.getPostText(), author, community);
        CommunityPost createdPost = communityPostRepository.save(communityPost);
        log.info("Пользователь {} добавил пост {}",
                author,
                createdPost);
        return createdPost;
    }

    public CommunityPost deletePost(Long id, Principal principal) {
        User author = userService.getCurrentUser(principal);
        CommunityPost communityPost = communityPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пост сообщества не найден или его не существует"));
        if (!communityPost.getAuthor().equals(author))
            throw new AccessDeniedException(accessDeniedMessage);
        communityPostRepository.deleteById(id);
        log.info("Пользователь {} удалил пост {}",
                author,
                communityPost);
        return communityPost;
    }

    public List<Community> find(String word) {
        if (word != null && !word.isEmpty())
            return communityRepository.findAllLikeName(word);
        return null;
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

    private void checkAuthorities(Long id, User creator) {
        Community community = getCommunityFromDB(id);
        if (!community.getCreator().getLogin().equals(creator.getLogin()))
            throw new AccessDeniedException(accessDeniedMessage);
    }

}
