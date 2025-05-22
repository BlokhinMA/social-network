package ru.sstu.socialnetworkbackend.services;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.auth.AuthRequest;
import ru.sstu.socialnetworkbackend.dtos.auth.AuthResponse;
import ru.sstu.socialnetworkbackend.entities.User;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final HttpServletRequest request;

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                       HttpServletRequest request) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.request = request;
    }

    public AuthResponse auth(AuthRequest authRequest) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.username(),
                    authRequest.password()
                )
            );
        } catch (Exception e) {
            LOG.info("Неудачная попытка авторизации с ip {}", request.getRemoteAddr());
            throw new BadCredentialsException("");
        }

        User user = userService.getUserByUsername(authRequest.username());

        LOG.info("Пользователь {} успешно авторизовался", user);

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(user, token);
    }

}
