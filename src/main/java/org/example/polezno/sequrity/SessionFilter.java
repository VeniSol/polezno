package org.example.polezno.sequrity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.polezno.Entities.User;
import org.example.polezno.Repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessionFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    public SessionFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
            String cookie = getCookie(request);
            if (cookie != null) {

                User user = userRepository.findByLogin(cookie);
                if (user==null) SecurityContextHolder.clearContext();
                else{
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            cookie,
                            null,
                            user.getRole().getAuthorities()
                    ));
                }
            }
            else SecurityContextHolder.clearContext();
        try {
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null)return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Haruka")) return cookie.getValue();
        }
        return null;
    }
}