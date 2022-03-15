package com.javabootcamp.assessment2.features.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private Date expiredDate;
}
