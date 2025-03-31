package ru.sstu.socialnetwork.services;

import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetwork.dtos.AuthRequest;
import ru.sstu.socialnetwork.dtos.AuthResponse;
import ru.sstu.socialnetwork.dtos.UserDto;
import ru.sstu.socialnetwork.entities.User;
import ru.sstu.socialnetwork.entities.enums.Role;
import ru.sstu.socialnetwork.exceptions.PasswordsNotMatchException;
import ru.sstu.socialnetwork.exceptions.UserAlreadyExistsException;
import ru.sstu.socialnetwork.repositories.UserRepository;

import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final Logger log = org.apache.logging.log4j.LogManager.getLogger(UserService.class);

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(UserDto userDto) {
        if (userRepository.findByLogin(userDto.getLogin()).isPresent()
                || userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException();
        if (!Objects.equals(userDto.getPassword(), userDto.getConfirmedPassword()))
            throw new PasswordsNotMatchException();
        User user = new User(
                userDto.getLogin(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getBirthDate(),
                passwordEncoder.encode(userDto.getPassword()),
                Role.ROLE_USER
        );
        user = userRepository.save(user);
        log.info("Пользователь {} зарегистрировался",
                user);
        String jwtToken = jwtUtil.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public String auth(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        User user = userRepository.findByLogin(authRequest.getUsername()).orElseThrow();
        String jwtToken = jwtUtil.generateToken(user);

        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();

        return jwtCookie.toString();
    }

}
