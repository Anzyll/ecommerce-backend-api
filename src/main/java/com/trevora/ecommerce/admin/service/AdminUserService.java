package com.trevora.ecommerce.admin.service;

import com.trevora.ecommerce.admin.exception.ProfileNotFoundException;
import com.trevora.ecommerce.common.entity.Profile;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.repository.ProfileRepository;
import com.trevora.ecommerce.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    public Page<User> getAllUsers(int page, int size, Sort sort) {
       return userRepository.findAll(PageRequest.of(page, size, sort));
    }

    public Profile getProfileByUser(Long userId) {
       return profileRepository.findByUser_UserId(userId)
               .orElseThrow(ProfileNotFoundException::new);
    }
}
