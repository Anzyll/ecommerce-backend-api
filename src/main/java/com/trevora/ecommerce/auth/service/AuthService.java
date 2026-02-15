package com.trevora.ecommerce.auth.service;

import com.trevora.ecommerce.auth.dto.LoginResponseDto;
import com.trevora.ecommerce.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    public LoginResponseDto verify(String email,String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );
        UserDetails user = (UserDetails) authentication.getPrincipal();

        assert user != null;
        String role = user.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        String token = jwtUtil.generateToken(user.getUsername(), role);

        return new LoginResponseDto(token);
    }

}

