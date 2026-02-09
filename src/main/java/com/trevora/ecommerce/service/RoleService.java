package com.trevora.ecommerce.service;

import com.trevora.ecommerce.entity.Role;
import com.trevora.ecommerce.exception.RoleNotFoundException;
import com.trevora.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role getDefaultRole(){
       return roleRepository.findByTitle("ROLE_USER")
               .orElseThrow(RoleNotFoundException::new);

    }

}
