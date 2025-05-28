package ru.sstu.socialnetworkbackend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.configs.SecurityUtil;
import ru.sstu.socialnetworkbackend.dtos.users.UserDto;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.entities.enums.Role;
import ru.sstu.socialnetworkbackend.exceptions.IncorrectDateException;
import ru.sstu.socialnetworkbackend.exceptions.PasswordsNotMatchException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceAlreadyExistsException;
import ru.sstu.socialnetworkbackend.exceptions.ResourceNotFoundException;
import ru.sstu.socialnetworkbackend.repositories.UserRepository;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    private static final String NOT_FOUND_MSG = "Пользователь не найден";

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MSG));
    }

    public User getCurrentUser() {
        return getUserByUsername(SecurityUtil.getUsername());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MSG));
    }

    public User create(UserDto userDto) {
        if (userRepository.findByUsername(userDto.username()).isPresent()
            || userRepository.findByEmail(userDto.email()).isPresent())
            throw new ResourceAlreadyExistsException("Пользователь с таким login или email уже существует");
        if (!userDto.password().equals(userDto.confirmedPassword()))
            throw new PasswordsNotMatchException();
        LocalDate birthDate = LocalDate.parse(userDto.birthDate());
        if (birthDate.isAfter(LocalDate.now()))
            throw new IncorrectDateException("Дата рождения должна быть либо в прошлом, либо в настоящем (сегодняшняя)");
        return userRepository.save(new User(
                userDto.username(),
                userDto.email(),
                userDto.firstName(),
                userDto.lastName(),
                birthDate,
                encoder.encode(userDto.password()),
                Role.ROLE_USER,
                false
            )
        );
    }

    public void update(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
