package ru.sstu.socialnetworkbackend.services;

import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.UserDto;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.entities.enums.Role;
import ru.sstu.socialnetworkbackend.exceptions.PasswordsNotMatchException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceAlreadyExistsException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.repositories.UserRepository;

import java.security.Principal;
import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    private final Logger log = org.apache.logging.log4j.LogManager.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователя не существует"));
    }

    public User getCurrentUser(Principal principal) {
        if (principal == null)
            throw new RuntimeException(""); // TODO: написать свое исключение
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }

    public User create(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()
                || userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("Пользователь с таким login или email уже существует");
        if (!userDto.getPassword().equals(userDto.getConfirmedPassword()))
            throw new PasswordsNotMatchException();
        User user = new User(
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                LocalDate.parse(userDto.getBirthDate()),
                encoder.encode(userDto.getPassword()),
                Role.ROLE_USER,
                false
        );
        user = userRepository.save(user);
        log.info("Пользователь {} зарегистрировался",
                user);
        return user;
    }

}
