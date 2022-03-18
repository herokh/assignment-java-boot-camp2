package com.javabootcamp.assessment2.features.auth;
import com.javabootcamp.assessment2.datamocks.AuthDataMock;
import com.javabootcamp.assessment2.utils.BCryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    private AuthService testService;

    @Mock
    private AuthRepository authRepositoryMock;

    @BeforeEach
    void setup() {
        lenient().when(authRepositoryMock.existsByUsername(Mockito.anyString()))
                .thenReturn(true);

        var userMock = AuthDataMock.createMocks();
        userMock.setPassword(BCryptUtil.hashString("1234"));
        lenient().when(authRepositoryMock.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(userMock));

        testService = new AuthService(authRepositoryMock);
    }

    @Test
    @DisplayName("Verify signIn that should be success")
    void test1() {
        var authRequest = new AuthRequest();
        var userMock = AuthDataMock.createMocks();
        authRequest.setUsername(userMock.getUsername());
        authRequest.setPassword(userMock.getPassword());
        AuthResponse authResponse = testService.signIn(authRequest);
        assertTrue(authResponse.getToken() != null);
    }
}
