package ru.sstu.socialnetwork.services;

import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.repositories.FriendshipRepository;

@Service
public class FriendService {

    private final FriendshipRepository friendshipRepository;

    public FriendService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public List<User> find(String word, Principal principal) {
        if (word != null && !word.isEmpty())
            return friendshipRepository.findAllLikeLoginOrFirstNameOrLastName(principal.getName(), word);
        return null;
    }

    public boolean create(String friendLogin, Principal principal) {
        if (userRepository.findByLogin(friendLogin) == null ||
                friendshipRepository.findByFriendLoginAndUserLogin(friendLogin, principal.getName()) != null)
            return false;
        Friendship friendship = new Friendship();
        friendship.setFirstUserLogin(principal.getName());
        friendship.setSecondUserLogin(friendLogin);
        Friendship createdFriendship = friendshipRepository.save(friendship);
        log.info("Пользователь {} добавил пользователя {} в друзья",
                userRepository.findByLogin(principal.getName()),
                userRepository.findByLogin(createdFriendship.getSecondUserLogin()));
        return true;
    }

    public List<User> showIncomingRequests(Principal principal) {
        return friendshipRepository.findIncomingRequestsByUserLogin(principal.getName());
    }

    public List<User> showOutgoingRequests(Principal principal) {
        return friendshipRepository.findOutgoingRequestsByUserLogin(principal.getName());
    }

    public void accept(Friendship friendship, Principal principal) {
        Friendship acceptedFriendship = friendshipRepository.accept(friendship);
        log.info("Пользователь {} принял заявку в друзья от пользователья {}",
                userRepository.findByLogin(principal.getName()),
                userRepository.findByLogin(acceptedFriendship.getFirstUserLogin()));
    }

    public List<User> show(Principal principal) {
        return friendshipRepository.findAllAcceptedByUserLogin(principal.getName());
    }

    public List<User> show(String userLogin) {
        return friendshipRepository.findAllAcceptedByUserLogin(userLogin);
    }

    public void delete(String friendLogin, Principal principal) {
        Friendship deletedFriendship =
                friendshipRepository.deleteByFriendLoginAndUserLogin(friendLogin, principal.getName());
        if (Objects.equals(friendLogin, deletedFriendship.getSecondUserLogin()))
            log.info("Пользователь {} удалил из друзей пользователя {}",
                    userRepository.findByLogin(principal.getName()),
                    userRepository.findByLogin(deletedFriendship.getSecondUserLogin()));
        else log.info("Пользователь {} удалил из друзей пользователя {}",
                userRepository.findByLogin(principal.getName()),
                userRepository.findByLogin(deletedFriendship.getFirstUserLogin()));
    }

    public void rejectFriend(String friendLogin, Principal principal) {
        Friendship deletedFriendship =
                friendshipRepository.deleteByFriendLoginAndUserLogin(friendLogin, principal.getName());
        if (Objects.equals(friendLogin, deletedFriendship.getSecondUserLogin()))
            log.info("Пользователь {} отклонил заявку в друзья от пользователя {}",
                    userRepository.findByLogin(principal.getName()),
                    userRepository.findByLogin(deletedFriendship.getSecondUserLogin()));
        else log.info("Пользователь {} отклонил заявку в друзья от пользователя {}",
                userRepository.findByLogin(principal.getName()),
                userRepository.findByLogin(deletedFriendship.getFirstUserLogin()));
    }

    public void deleteOutgoingRequest(String friendLogin, Principal principal) {
        Friendship deletedFriendship =
                friendshipRepository.deleteByFriendLoginAndUserLogin(friendLogin, principal.getName());
        if (Objects.equals(friendLogin, deletedFriendship.getSecondUserLogin()))
            log.info("Пользователь {} удалил заявку в друзья пользователю {}",
                    userRepository.findByLogin(principal.getName()),
                    userRepository.findByLogin(deletedFriendship.getSecondUserLogin()));
        else log.info("Пользователь {} удалил заявку в друзья пользователю {}",
                userRepository.findByLogin(principal.getName()),
                userRepository.findByLogin(deletedFriendship.getFirstUserLogin()));
    }

    public List<Friendship> showAll() {
        return friendshipRepository.findAll();
    }

}
