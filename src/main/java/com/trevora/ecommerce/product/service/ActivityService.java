package com.trevora.ecommerce.product.service;

import com.trevora.ecommerce.admin.exception.ActivityNotFoundException;
import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    public Activity getActivityById(Long activityId) {
      return   activityRepository.findById(activityId)
              .orElseThrow(ActivityNotFoundException::new);
    }
}
