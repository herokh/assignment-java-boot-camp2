package com.javabootcamp.assessment2.configurations;

import com.javabootcamp.assessment2.ApplicationContext;
import com.javabootcamp.assessment2.features.auth.AuthRepository;
import com.javabootcamp.assessment2.features.auth.UserNotFoundException;
import com.javabootcamp.assessment2.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = request.getHeader(AUTHORIZATION_HEADER).replace("Bearer ", "");
            var username = JwtUtil.getUsernameFromToken(jwtToken);
            var user = authRepository.findByUsername(username).orElseThrow(
                    () -> new UserNotFoundException("User not found."));
            applicationContext.setCurrentUserIfNotExists(user);
            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException e)
        {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
        catch (SignatureException e)
        {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return !path.startsWith("/api/secure/");
    }
}
