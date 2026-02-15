package com.trevora.ecommerce.auth.service;

import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.auth.entity.RefreshToken;
import com.trevora.ecommerce.auth.repository.RefreshTokenRepository;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.security.CustomUserDetails;
import com.trevora.ecommerce.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    public LoginResponseDto verify(String email, String password,
                                   HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities()
                .iterator().next().getAuthority();

        String accessToken =
                jwtUtil.generateToken(userDetails.getUsername(), role);

        RefreshToken refreshToken =
                refreshTokenService.create(userDetails.getUser());

        ResponseCookie cookie = ResponseCookie.from(
                        "refreshToken", refreshToken.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new LoginResponseDto(accessToken);
    }


    public LoginResponseDto refresh(String token) {
        RefreshToken refreshToken = refreshTokenService.verify(token);
        User user = refreshToken.getUser();
        String role = user.getRole().getTitle();
        String newAccessToken = jwtUtil.generateToken(user.getEmail(), role);
        return new LoginResponseDto(newAccessToken);
    }
}

