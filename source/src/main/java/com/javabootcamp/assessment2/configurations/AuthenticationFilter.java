package com.javabootcamp.assessment2.configurations;

import com.javabootcamp.assessment2.ApplicationContext;
import com.javabootcamp.assessment2.entities.User;
import com.javabootcamp.assessment2.exceptions.UnauthorizedUserException;
import com.javabootcamp.assessment2.features.auth.AuthRepository;
import com.javabootcamp.assessment2.features.auth.UserNotFoundException;
import com.javabootcamp.assessment2.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String API_SECURE = "/api/secure/";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = request.getHeader(AUTHORIZATION_HEADER);
            if (jwtToken == null) {
                throw new JwtException("JWT not found.");
            }
            jwtToken = jwtToken.replace("Bearer ", "");
            var username = JwtUtil.getUsernameFromToken(jwtToken);
            var user = authRepository.findByUsername(username).orElseThrow(
                    () -> new UserNotFoundException("User not found."));

            if (!checkAuthorizedUser(request, user)) {
                throw new UnauthorizedUserException("Unauthorized user.");
            }

            applicationContext.setCurrentUserIfNotExists(user);
            filterChain.doFilter(request, response);
        }
        catch (JwtException e)
        {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        Boolean shouldFilter = path.startsWith(API_SECURE);
        return !shouldFilter;
    }

    // TODO: I don't know how to make it better... but this is the fastest way for now.
    private boolean checkAuthorizedUser(HttpServletRequest request, User currentUser) {
        String path = request.getServletPath().replace(API_SECURE, "").split("/")[0].toLowerCase();
        if (currentUser.isIoT()) {
            var routes = new String[] {"trucklocationpaths"};
            return Arrays.asList(routes).contains(path);
        }
        else if (currentUser.isBranchUser()) {
            var routes = new String[] {"assets", "accountingreports"};
            return Arrays.asList(routes).contains(path);
        }
        else if (currentUser.isCashCenterUser()) {
            var routes = new String[] {"assets", "accountingreports"};
            return Arrays.asList(routes).contains(path);
        }
        return false;
    }
}
