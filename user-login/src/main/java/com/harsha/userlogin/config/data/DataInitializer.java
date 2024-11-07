package com.harsha.userlogin.config.data;

import com.harsha.userlogin.domain.Permission;
import com.harsha.userlogin.domain.Role;
import com.harsha.userlogin.domain.enums.RoleType;
import com.harsha.userlogin.repository.PermissionRepository;
import com.harsha.userlogin.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;

    @Override
    public void run(String... args) {
        // Define required permissions
        Permission createProject = getOrCreatePermission("CREATE_PROJECT", "To create a new project");
        Permission deleteProject = getOrCreatePermission("DELETE_PROJECT", "To delete an existing Project");
        Permission viewProject = getOrCreatePermission("VIEW_PROJECT", "To view projects");

        // Define required roles and assign permissions
        assignPermissionsToRole(RoleType.ROLE_ADMIN, Set.of(createProject, deleteProject, viewProject));
        assignPermissionsToRole(RoleType.ROLE_USER, Set.of(viewProject));
    }

    private Permission getOrCreatePermission(String name, String description) {
        return permissionRepo.findByName(name)
                .orElseGet(() -> permissionRepo.save(new Permission(name, description)));
    }

    private void assignPermissionsToRole(RoleType roleType, Set<Permission> requiredPermissions) {
        Role role = roleRepo.findByName(roleType).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(roleType);
            return roleRepo.save(newRole);
        });

        // Check if the role already has the required permissions
        Set<Permission> currentPermissions = role.getPermissions();
        if (!currentPermissions.equals(requiredPermissions)) {
            role.setPermissions(requiredPermissions);  // Update permissions if different
            roleRepo.save(role); // Save changes to the role
        }
    }
}