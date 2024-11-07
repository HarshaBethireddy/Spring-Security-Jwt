package com.harsha.userlogin.service.user;

import com.harsha.userlogin.domain.User;
import com.harsha.userlogin.web.request.PasswordChangeRequest;
import com.harsha.userlogin.web.response.UserResponse;
import com.harsha.userlogin.web.request.UserUpdateRequest;
import com.harsha.userlogin.exception.custom.InvalidPasswordException;
import com.harsha.userlogin.exception.custom.PasswordMismatchException;
import com.harsha.userlogin.exception.custom.UserAlreadyExistsException;
import com.harsha.userlogin.exception.custom.UserNotFoundException;
import com.harsha.userlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmailWithRolesAndPermissions(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserResponse.fromUser(user);
    }

    @Transactional
    public UserResponse updateUser(String email, UserUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!email.equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());

        return UserResponse.fromUser(userRepository.save(user));
    }

    @Transactional
    public void changePassword(String email, PasswordChangeRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password in incorrect");
        }

        if(!request.newPassword().equals(request.confirmPassword())) {
            throw new PasswordMismatchException("New password does not match confirm password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
}
