package com.harsha.userlogin.config.data;


import com.harsha.userlogin.domain.Role;
import com.harsha.userlogin.domain.enums.RoleType;
import com.harsha.userlogin.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void seedRoles() {
        if (roleRepository.count() == 0) {
            Arrays.stream(RoleType.values())
                    .forEach(roleType -> roleRepository.save(new Role(roleType)));
        }
    }
}
