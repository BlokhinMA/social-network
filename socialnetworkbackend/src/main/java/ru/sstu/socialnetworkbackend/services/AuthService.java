package ru.sstu.socialnetworkbackend.services;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.AuthRequest;
import ru.sstu.socialnetworkbackend.dtos.AuthResponse;
import ru.sstu.socialnetworkbackend.entities.User;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse auth(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password()
                )
        );

        User user = userService.getUserByUsername(authRequest.username());

        log.info("Пользователь {} успешно авторизовался", user);

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(user, token);
    }

}
