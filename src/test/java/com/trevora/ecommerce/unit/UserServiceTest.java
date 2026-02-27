package com.trevora.ecommerce.unit;

import com.trevora.ecommerce.auth.exception.UserAlreadyExistsException;
import com.trevora.ecommerce.common.entity.User;

import com.trevora.ecommerce.common.repository.UserRepository;
import com.trevora.ecommerce.common.service.RoleService;
import com.trevora.ecommerce.common.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private UserService userService;

    private User user;
    private String rawPassword;

    @BeforeEach
    void setup(){
         user = new User();
        user.setEmail("test@gmail.com");
        rawPassword = "Java@123";
    }

    @Test
    void register_validUser_should_saveUser() {

        when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(rawPassword))
                .thenReturn("encoded password");
        when(roleService.getDefaultRole())
                .thenReturn(null);
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        User savedUser = userService.register(user, rawPassword);
        assertNotNull(savedUser);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(rawPassword);
        verify(roleService).getDefaultRole();
    }

    @Test
    void register_existingEmail_should_throw_exception() {
        when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(true);
        assertThrows(UserAlreadyExistsException.class,
                ()->userService.register(user,rawPassword));
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder,never()).encode(any(String.class));

    }
}
