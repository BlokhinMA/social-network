package ru.sstu.socialnetworkbackend.services;

import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.dtos.AuthRequest;
import ru.sstu.socialnetworkbackend.entities.User;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final Logger log = org.apache.logging.log4j.LogManager.getLogger(AuthService.class);

    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String auth(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        User user = userService.getUserByUsername(authRequest.getUsername());

        log.info("Пользователь {} успешно авторизовался", user);

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
