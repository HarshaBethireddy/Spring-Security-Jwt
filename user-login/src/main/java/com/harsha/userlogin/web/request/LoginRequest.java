package com.harsha.userlogin.web.request;

public record LoginRequest(
        String email,
        String password
) {}
