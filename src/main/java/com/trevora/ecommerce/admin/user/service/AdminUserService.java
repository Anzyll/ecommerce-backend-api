package com.trevora.ecommerce.admin.user.service;

import com.trevora.ecommerce.admin.exception.ProfileNotFoundException;
import com.trevora.ecommerce.common.entity.Profile;
import com.trevora.ecommerce.common.entity.User;
import com.trevora.ecommerce.common.repository.ProfileRepository;
import com.trevora.ecommerce.common.repository.UserRepository;
import com.trevora.ecommerce.order.entity.Order;
import com.trevora.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final OrderRepository orderRepository;
    public Page<User> getAllUsers(int page, int size, Sort sort) {
       Page<User> users = userRepository.findAll(PageRequest.of(page, size, sort));
       log.info("admin fetched all users list | page={} size={} sort={} ",page,size,sort);
       return users;
    }

    public Profile getProfileByUser(Long userId) {
       Profile profile = profileRepository.findByUser_UserId(userId)
               .orElseThrow(ProfileNotFoundException::new);
       log.info("admin viewed profile for userId={}",userId);
       return profile;
    }

    public List<Order> getOrdersByUser(Long userId) {
       List<Order> orders = orderRepository.findAllByUser_UserId_OrderByCreatedAtDesc(userId);
       log.info("admin view orders of userId={}",userId);
       return orders;
    }
}
