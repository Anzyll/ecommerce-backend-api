package com.trevora.ecommerce.common.service;

import com.trevora.ecommerce.common.entity.Role;
import com.trevora.ecommerce.common.exception.RoleNotFoundException;
import com.trevora.ecommerce.common.repository.RoleRepository;
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
