package com.harsha.userlogin.web.request;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String email
) {}
