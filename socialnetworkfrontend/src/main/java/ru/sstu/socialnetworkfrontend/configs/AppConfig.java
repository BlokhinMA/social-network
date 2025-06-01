package ru.sstu.socialnetworkfrontend.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Filter appFilter() {
        return (ServletRequest request, ServletResponse response, FilterChain chain) -> {
            if (response instanceof HttpServletResponse httpResponse) {
                httpResponse.setHeader("X-Frame-Options", "DENY");
                httpResponse.setHeader("X-Content-Type-Options", "nosniff");
            }
            chain.doFilter(request, response);
        };
    }

}
