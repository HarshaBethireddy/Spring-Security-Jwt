package com.harsha.userlogin.web.controller;

import com.harsha.userlogin.service.auth.AuthenticationService;
import com.harsha.userlogin.web.request.LoginRequest;
import com.harsha.userlogin.web.request.SignUpRequest;
import com.harsha.userlogin.web.response.JwtResponse;
import com.harsha.userlogin.web.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/signup")
    @Operation(summary = "Register new user")
    public ResponseEntity<JwtResponse> signup(@Valid @RequestBody SignUpRequest request) {
        return new ResponseEntity<>(authService.signUp(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user")
    public ResponseEntity<MessageResponse> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        authService.logout(jwt);
        return ResponseEntity.ok(MessageResponse.success("Logged out successfully"));
    }
}
