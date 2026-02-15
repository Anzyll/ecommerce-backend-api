package com.trevora.ecommerce.common.service;

import com.trevora.ecommerce.common.exception.UserNotFoundException;
import com.trevora.ecommerce.common.repository.UserRepository;
import com.trevora.ecommerce.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  userRepository.findByEmailWithRole(email)
                .map(CustomUserDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }
}
