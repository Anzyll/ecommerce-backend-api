package com.trevora.ecommerce.product.repository;

import com.trevora.ecommerce.product.entity.Activity;
import com.trevora.ecommerce.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Optional<Activity> findByName(String name);
}
