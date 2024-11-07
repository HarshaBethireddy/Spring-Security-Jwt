package com.harsha.userlogin.web.request;

public record PasswordChangeRequest(
        String currentPassword,
        String newPassword,
        String confirmPassword
) {}
