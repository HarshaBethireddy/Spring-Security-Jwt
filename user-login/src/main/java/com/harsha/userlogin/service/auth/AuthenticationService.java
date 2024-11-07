package com.harsha.userlogin.service.auth;

import com.harsha.userlogin.domain.Permission;
import com.harsha.userlogin.domain.Role;
import com.harsha.userlogin.domain.User;
import com.harsha.userlogin.web.response.JwtResponse;
import com.harsha.userlogin.web.request.LoginRequest;
import com.harsha.userlogin.web.request.SignUpRequest;
import com.harsha.userlogin.domain.enums.RoleType;
import com.harsha.userlogin.exception.custom.RoleNotFoundException;
import com.harsha.userlogin.exception.custom.UserAlreadyExistsException;
import com.harsha.userlogin.repository.RoleRepository;
import com.harsha.userlogin.repository.UserRepository;
import com.harsha.userlogin.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional
    public JwtResponse signUp(SignUpRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException("Email already exists");
        }

        RoleType roleType = request.roleType() != null ? request.roleType() : RoleType.ROLE_USER;

        Role userRole = roleRepository.findByName(roleType)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(userRole))
                .build();


        userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return JwtResponse.of(
                accessToken,
                refreshToken,
                "Bearer",
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()),
                user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(Permission::getName)
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmailWithRolesAndPermissions(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return JwtResponse.of(
                accessToken,
                refreshToken,
                "Bearer",
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()),
                user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(Permission::getName)
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public void logout(String token) {
        tokenBlacklistService.blacklistToken(token);
    }
}
