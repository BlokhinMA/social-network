package ru.sstu.socialnetworkbackend.configs;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import ru.sstu.socialnetworkbackend.entities.User;
import ru.sstu.socialnetworkbackend.services.JwtUtil;
import ru.sstu.socialnetworkbackend.services.UserService;

@Service
public class LogoutService implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(LogoutService.class);

    public LogoutService(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final Cookie[] cookies = request.getCookies();
        String jwt = "";
        for (Cookie cookie : cookies)
            if (cookie.getName().equals("jwt")) {
                jwt = cookie.getValue();
                break;
            }
        String username = jwtUtil.extractUsername(jwt);
        User user = userService.getUserByUsername(username);
        ResponseCookie responseCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        LOG.info("Пользователь {} вышел из системы", user);
    }

}
