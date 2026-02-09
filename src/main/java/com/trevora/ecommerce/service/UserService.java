package com.trevora.ecommerce.service;

import com.trevora.ecommerce.entity.Role;
import com.trevora.ecommerce.entity.User;
import com.trevora.ecommerce.exception.InvalidCredentialException;
import com.trevora.ecommerce.exception.UserAlreadyExistsException;
import com.trevora.ecommerce.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    public User register(User user,  String password) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleService.getDefaultRole();
        user.setRole(role);
        return userRepository.save(user);
    }

    public void login(String email,String rawPassword) {
       User userFound = userRepository.findByEmail(email)
                       .orElseThrow(InvalidCredentialException::new);
       if(!passwordEncoder.matches(rawPassword,userFound.getPassword())){
           throw new InvalidCredentialException();
       }

    }

}