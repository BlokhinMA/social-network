package ru.sstu.socialnetworkbackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.friends.FriendsResponseDto;
import ru.sstu.socialnetworkbackend.entities.Friendship;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.exceptions.IncorrectKeywordException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.repositories.FriendshipRepository;

import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(FriendshipService.class);
    private static final String NOT_FOUNT_OR_ALREADY_EXISTS_FRIENDSHIP_MSG = "Дружбы не существует или вы уже друзья";

    public FriendshipService(FriendshipRepository friendshipRepository, UserService userService) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
    }

    public Friendship create(Long friendId) {
        User user = userService.getCurrentUser();
        User friend = userService.getUserById(friendId);
        Friendship friendship = new Friendship(
            user,
            friend,
            false
        );
        Friendship createdFriendship = friendshipRepository.save(friendship);
        LOG.info("Пользователь {} отправил запрос на дружбу пользователю {}",
            user,
            friend);
        return createdFriendship;
    }

    public List<User> find(String keyword) {
        if (keyword.isEmpty())
            throw new IncorrectKeywordException();
        User user = userService.getCurrentUser();
        return friendshipRepository.findAllLikeFirstNameOrLastName(keyword, user.getId());
    }

    public List<User> showIncomingRequests() {
        User user = userService.getCurrentUser();
        return friendshipRepository.findIncomingRequestsByUserId(user.getId());
    }

    public List<User> showOutgoingRequests() {
        User user = userService.getCurrentUser();
        return friendshipRepository.findOutgoingRequestsByUserId(user.getId());
    }

    public Friendship accept(Long userId) {
        User currentUser = userService.getCurrentUser();
        User user = userService.getUserById(userId);
        Friendship friendship = friendshipRepository.findNotAcceptedByFirstUserIdAndSecondUserId(user.getId(),
                        currentUser.getId())
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUNT_OR_ALREADY_EXISTS_FRIENDSHIP_MSG));
        friendship.setAccepted(true);
        Friendship acceptedFriendship = friendshipRepository.save(friendship);
        LOG.info("Пользователь {} принял заявку в друзья от пользователя {}",
            currentUser,
            user);
        return acceptedFriendship;
    }

    public List<User> show() {
        User user = userService.getCurrentUser();
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

    public Friendship delete(Long friendId) {
        User user = userService.getCurrentUser();
        User friend = userService.getUserById(friendId);
        Friendship friendship = friendshipRepository.findAcceptedByFirstUserIdAndSecondUserId(user.getId(),
                        friend.getId())
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUNT_OR_ALREADY_EXISTS_FRIENDSHIP_MSG));
        friendshipRepository.deleteById(friendship.getId());
        LOG.info("Пользователь {} удалил из друзей пользователя {}",
            user,
            friend);
        return friendship;
    }

    public Friendship reject(Long userId) {
        User currentUser = userService.getCurrentUser();
        User user = userService.getUserById(userId);
        Friendship friendship = friendshipRepository.findNotAcceptedByFirstUserIdAndSecondUserId(user.getId(),
                        currentUser.getId())
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUNT_OR_ALREADY_EXISTS_FRIENDSHIP_MSG));
        friendshipRepository.deleteById(friendship.getId());
        LOG.info("Пользователь {} отклонил заявку в друзья от пользователя {}",
            currentUser,
            user);
        return friendship;
    }

    public Friendship deleteOutgoingRequest(Long userId) {
        User currentUser = userService.getCurrentUser();
        User user = userService.getUserById(userId);
        Friendship friendship = friendshipRepository.findNotAcceptedByFirstUserIdAndSecondUserId(currentUser.getId(),
                        user.getId())
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUNT_OR_ALREADY_EXISTS_FRIENDSHIP_MSG));
        friendshipRepository.deleteById(friendship.getId());
        LOG.info("Пользователь {} удалил заявку в друзья от пользователя {}",
            currentUser,
            user);
        return friendship;
    }

    public boolean areFriends(Long firstUserId, Long secondUserId) {
        return friendshipRepository.findAcceptedByFirstUserIdAndSecondUserId(firstUserId, secondUserId).isPresent();
    }

}
