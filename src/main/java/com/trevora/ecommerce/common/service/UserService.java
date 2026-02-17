package com.trevora.ecommerce.common.service;

import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.auth.exception.UserAlreadyExistsException;
import com.trevora.ecommerce.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    @Transactional
    public User register(User user,  String password) {
        log.info("attempting user registration for email={}",user.getEmail());
        if(userRepository.existsByEmail(user.getEmail())){
            log.warn("User already exists: email={}", user.getEmail());
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleService.getDefaultRole();
        user.setRole(role);
        return userRepository.save(user);

    }

}