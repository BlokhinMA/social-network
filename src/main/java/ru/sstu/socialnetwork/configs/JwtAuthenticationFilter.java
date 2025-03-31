package ru.sstu.socialnetwork.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.sstu.socialnetwork.services.JwtUtil;
import ru.sstu.socialnetwork.services.UserService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final Logger log = org.apache.logging.log4j.LogManager.getLogger(UserService.class);

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    )
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        StringBuilder stringBuilder = new StringBuilder();
        for (Cookie cookie : cookies) {
            stringBuilder.append(cookie.getName());
            stringBuilder.append(": ");
            stringBuilder.append(cookie.getValue());
        }
        log.info(stringBuilder.toString());
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }



//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("jwt_token".equals(cookie.getName())) { // Проверяем куку по имени
//                    String jwtToken = cookie.getValue();
//                    System.out.println("JWT Token найден: " + jwtToken);
//                    // Тут можно добавить в SecurityContext, если необходимо
//                    break;
//                }
//            }
//        }

        final String jwt = authHeader.substring(7);
        final String userLogin = jwtUtil.extractUsername(jwt);
        if (userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userLogin);
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
