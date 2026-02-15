package com.trevora.ecommerce.common.repository;

import com.trevora.ecommerce.common.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByAddressIdAndUser_UserId(Long addressId, Long userUserId);
}
