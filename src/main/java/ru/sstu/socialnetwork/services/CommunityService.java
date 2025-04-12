package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.dtos.CheckIsDto;
import ru.sstu.socialnetwork.dtos.CommunityDto;
import ru.sstu.socialnetwork.dtos.CommunityPostDto;
import ru.sstu.socialnetwork.entities.Community;
import ru.sstu.socialnetwork.entities.CommunityMember;
import ru.sstu.socialnetwork.entities.CommunityPost;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.exceptions.IncorrectRequestValuesException;
import ru.sstu.socialnetwork.exceptions.ResourceAlreadyExistsException;
import ru.sstu.socialnetwork.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetwork.repositories.CommunityMemberRepository;
import ru.sstu.socialnetwork.repositories.CommunityPostRepository;
import ru.sstu.socialnetwork.repositories.CommunityRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

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

    public List<Community> showAll(Long memberId) {
        User member = userService.getUserById(memberId);
        return communityRepository.findAllByMemberId(member.getId());
    }

    public Community show(Long id) {
        return getCommunityFromDB(id);
    }

    public List<CommunityMember> showAllMembers(Long communityId) {
        Community community = getCommunityFromDB(communityId);
        return communityMemberRepository.findAllByCommunity(community);
    }

    public List<CommunityPost> showAllPosts(Long communityId) {
        Community community = getCommunityFromDB(communityId);
        return communityPostRepository.findAllByCommunity(community);
    }

    public Community create(CommunityDto communityDto, Principal principal) {
        User creator = userService.getCurrentUser(principal);
        Community community = new Community();
        community.setName(communityDto.getName());
        community.setCreator(creator);
        Community createdCommunity = communityRepository.save(community);
        log.info("Пользователь {} добавил сообщество {}",
                creator,
                createdCommunity);
        return createdCommunity;
    }

    public Community delete(Long id, Principal principal) {
        User creator = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(id);
        if (!isCreator(id, principal))
            throw new AccessDeniedException(accessDeniedMessage);
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
        CommunityMember communityMember = new CommunityMember();
        communityMember.setMember(member);
        communityMember.setCommunity(community);
        CommunityMember joinedCommunityMember = communityMemberRepository.save(communityMember);
        log.info("Пользователь {} стал участником сообщества {}",
                member,
                joinedCommunityMember);
        return joinedCommunityMember;
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
        if (!isCreator(communityMember.getCommunity().getId(), principal))
            throw new AccessDeniedException(accessDeniedMessage);
        communityMemberRepository.deleteById(id);
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
        CommunityPost communityPost = new CommunityPost();
        communityPost.setPostText(communityPostDto.getPostText());
        communityPost.setAuthor(author);
        communityPost.setCommunity(community);
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
        if (!Objects.equals(communityPost.getAuthor(), author))
            throw new AccessDeniedException(accessDeniedMessage);
        communityPostRepository.deleteById(id);
        log.info("Пользователь {} удалил пост {}",
                author,
                communityPost);
        return communityPost;
    }

    public List<Community> find(String word) {
        if (word.isEmpty())
            throw new IncorrectRequestValuesException("Некорректное ключевое слово");
        return communityRepository.findAllLikeName(word);
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

    private boolean isMember(Principal principal, Long communityId) {
        User currentUser = userService.getCurrentUser(principal);
        Community community = getCommunityFromDB(communityId);
        return communityMemberRepository.findByMemberAndCommunity(currentUser, community).isPresent();
    }

    private boolean isCreator(Long id, Principal principal) {
        Community community = getCommunityFromDB(id);
        return Objects.equals(community.getCreator().getLogin(), principal.getName());
    }

    public CheckIsDto checkIsMember(Principal principal, Long communityId) {
        return new CheckIsDto(isMember(principal, communityId));
    }

    public CheckIsDto checkIsCreator(Long id, Principal principal) {
        return new CheckIsDto(isCreator(id, principal));
    }

}
