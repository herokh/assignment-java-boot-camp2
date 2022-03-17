package com.javabootcamp.assessment2.features.auth;

import com.javabootcamp.assessment2.utils.BCryptUtil;
import com.javabootcamp.assessment2.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private static final int JWT_TOKEN_USER_VALIDITY =  60 * 60 * 1000;
    private static final int JWT_TOKEN_IOT_VALIDITY =  60 * 60 * 10000;

    @Autowired
    private AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public AuthService(){}

    public AuthResponse signIn(AuthRequest authRequest) {
        var hasUser = authRepository.existsByUsername(authRequest.getUsername());
        if (!hasUser) {
            throw new UserNotFoundException("User not found.");
        }
        var user = authRepository.findByUsername(authRequest.getUsername()).get();
        var isPasswordValid = BCryptUtil.validateHashString(authRequest.getPassword(), user.getPassword());
        if (!isPasswordValid) {
            throw new UserPasswordInvalidException("Password invalid.");
        }

        Date expiredDate = null;
        if (user.isIoT()) {
            expiredDate = new Date(System.currentTimeMillis() + JWT_TOKEN_IOT_VALIDITY);
        }
        else {
            expiredDate = new Date(System.currentTimeMillis() + (JWT_TOKEN_USER_VALIDITY));
        }
        var response = new AuthResponse();
        response.setExpiredDate(expiredDate);
        response.setToken(JwtUtil.generateToken(user, expiredDate));
        return response;
    }
}
