package ru.sstu.socialnetwork.services;

import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetwork.repositories.UserRepository;

import java.security.Principal;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser(Principal principal) {
        if (principal == null)
            throw new RuntimeException(""); // todo написать свое исключение
        return userRepository.findByLogin(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

//    public void signOut(Principal principal) {
//        User user = userRepository.findByLogin(principal.getName());
//        log.info("Пользователь {} вышел из системы",
//                user);
//    }
//
//    public List<User> showAll() {
//        return userRepository.findAll();
//    }

}
