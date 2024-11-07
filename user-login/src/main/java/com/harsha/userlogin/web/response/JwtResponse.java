package com.harsha.userlogin.web.response;

import java.util.Set;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        String email,
        Set<String> roles,
        Set<String> permissions
) {
    public static JwtResponse of(String accessToken, String refreshToken, String tokenType, String email, Set<String> roles, Set<String> permissions) {
        return new JwtResponse(accessToken, refreshToken, "Bearer", email, roles, permissions);
    }
}
