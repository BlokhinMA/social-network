package ru.sstu.socialnetworkbackend.configs;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class SecurityUtil {

    private SecurityUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static Principal getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUsername() {
        if (getPrincipal() == null) {
            throw new AuthenticationCredentialsNotFoundException("Пользователь не авторизован");
        }
        return getPrincipal().getName();
    }

}
