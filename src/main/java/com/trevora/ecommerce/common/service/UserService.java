package com.trevora.ecommerce.common.service;

import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.auth.exception.UserAlreadyExistsException;
import com.trevora.ecommerce.common.exception.PasswordMismatchException;
import com.trevora.ecommerce.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    @Transactional
    public User register(User user,  String password,String confirmPassword) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException();
        }
        if(!password.equals(confirmPassword)){
            throw new PasswordMismatchException();
        }
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleService.getDefaultRole();
        user.setRole(role);
        return userRepository.save(user);
    }

}