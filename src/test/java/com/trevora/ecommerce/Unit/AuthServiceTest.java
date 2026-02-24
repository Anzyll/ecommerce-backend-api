package com.trevora.ecommerce.Unit;

import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.auth.entity.RefreshToken;
import com.trevora.ecommerce.auth.service.AuthService;
import com.trevora.ecommerce.auth.service.RefreshTokenService;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.security.CustomUserDetails;
import com.trevora.ecommerce.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private HttpServletResponse httpServletResponse;
    @InjectMocks
    private  AuthService authService;
    private String email;
    private String password;
    private  User user;
    @BeforeEach
    void setup(){
        email = "test@example.com";
        password = "password";

        user = new User();
        user.setUserId(1L);
        user.setEmail(email);
    }

    @Test
    void login_validCredentials_shouldReturnToken() {

        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);
        when(authentication.getPrincipal())
                .thenReturn(userDetails);
        when(userDetails.getUsername())
                .thenReturn(email);

        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));

        doReturn(authorities)
                .when(userDetails)
                .getAuthorities();

        when(userDetails.getUser())
                .thenReturn(user);

        when(jwtUtil.generateToken(email, "ROLE_USER"))
                .thenReturn("accessToken");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");

        when(refreshTokenService.create(user))
                .thenReturn(refreshToken);

        LoginResponseDto result =
                authService.verify(email, password, httpServletResponse);

        assertNotNull(result);
        assertEquals("accessToken", result.token());

        verify(httpServletResponse)
                .addHeader(eq(HttpHeaders.SET_COOKIE), anyString());
    }


    @Test
    void login_invalidCredential_shouldThrow_Exception(){

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class,()->authService.verify(email,password,httpServletResponse));

        verify(jwtUtil,never()).generateToken(any(),any());
    }

    @Test
    void login_userNotFound_shouldThrow_Exception(){

        when(authenticationManager.authenticate(any()))
                .thenThrow(new UsernameNotFoundException("username not found"));
        assertThrows(UsernameNotFoundException.class,()->authService.verify(email,password,httpServletResponse));
        verify(jwtUtil,never()).generateToken(any(),any());
    }

}
