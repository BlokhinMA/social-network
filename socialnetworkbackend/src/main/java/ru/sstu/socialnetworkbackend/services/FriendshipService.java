package ru.sstu.socialnetworkbackend.services;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.FriendsResponseDto;
import ru.sstu.socialnetworkbackend.entities.Friendship;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.exceptions.IncorrectKeywordException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.repositories.FriendshipRepository;

import java.security.Principal;
import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FriendshipService.class);

    public FriendshipService(FriendshipRepository friendshipRepository, UserService userService) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
    }

    public Friendship create(Long friendId, Principal principal) {
        User user = userService.getCurrentUser(principal);
        User friend = userService.getUserById(friendId);
        Friendship friendship = new Friendship(
                user,
                friend,
                false
        );
        Friendship createdFriendship = friendshipRepository.save(friendship);
        log.info("Пользователь {} отправил запрос на дружбу пользователю {}",
                user,
                friend);
        return createdFriendship;
    }

    public List<User> find(String keyword, Principal principal) {
        if (keyword.isEmpty())
            throw new IncorrectKeywordException();
        User user = userService.getCurrentUser(principal);
        return friendshipRepository.findAllLikeFirstNameOrLastName(keyword, user.getId());
    }

    public List<User> showIncomingRequests(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return friendshipRepository.findIncomingRequestsByUserId(user.getId());
    }

    public List<User> showOutgoingRequests(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return friendshipRepository.findOutgoingRequestsByUserId(user.getId());
    }

    public Friendship accept(Long userId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        User user = userService.getUserById(userId);
        Friendship friendship = friendshipRepository.findNotAcceptedByFirstUserIdAndSecondUserId(user.getId(), currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Дружбы не существует или вы уже друзья"));
        friendship.setAccepted(true);
        Friendship acceptedFriendship = friendshipRepository.save(friendship);
        log.info("Пользователь {} принял заявку в друзья от пользователя {}",
                currentUser,
                user);
        return acceptedFriendship;
    }

    public List<User> show(Principal principal) {
        User user = userService.getCurrentUser(principal);
        return friendshipRepository.findAllAcceptedByUserId(user.getId());
    }

    public FriendsResponseDto show(Long userId) {
        User user = userService.getUserById(userId);
        List<User> friends = friendshipRepository.findAllAcceptedByUserId(user.getId());
        return new FriendsResponseDto(
                user,
                friends
        );
    }

    public Friendship delete(Long friendId, Principal principal) {
        User user = userService.getCurrentUser(principal);
        User friend = userService.getUserById(friendId);
        Friendship friendship = friendshipRepository.findAcceptedByFirstUserIdAndSecondUserId(user.getId(), friend.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Дружбы не существует или заявка в друзья не подтверждена"));
        friendshipRepository.deleteById(friendship.getId());
        log.info("Пользователь {} удалил из друзей пользователя {}",
                user,
                friend);
        return friendship;
    }

    public Friendship reject(Long userId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        User user = userService.getUserById(userId);
        Friendship friendship = friendshipRepository.findNotAcceptedByFirstUserIdAndSecondUserId(user.getId(), currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Дружбы не существует или вы уже друзья"));
        friendshipRepository.deleteById(friendship.getId());
        log.info("Пользователь {} отклонил заявку в друзья от пользователя {}",
                currentUser,
                user);
        return friendship;
    }

    public Friendship deleteOutgoingRequest(Long userId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        User user = userService.getUserById(userId);
        Friendship friendship = friendshipRepository.findNotAcceptedByFirstUserIdAndSecondUserId(currentUser.getId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Дружбы не существует или вы уже друзья"));
        friendshipRepository.deleteById(friendship.getId());
        log.info("Пользователь {} удалил заявку в друзья пользователю {}",
                currentUser,
                user);
        return friendship;
    }

    public boolean isFriend(Long userId, Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        User user = userService.getUserById(userId);
        return friendshipRepository.findAcceptedByFirstUserIdAndSecondUserId(currentUser.getId(), user.getId()).isPresent();
    }

}
