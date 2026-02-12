package com.trevora.ecommerce.service;

import com.trevora.ecommerce.exception.UserNotFoundException;
import com.trevora.ecommerce.repository.UserRepository;
import com.trevora.ecommerce.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return  userRepository.findByEmailWithRole(email)
                .map(CustomUserDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }
}
