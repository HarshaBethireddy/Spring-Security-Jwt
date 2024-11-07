package com.harsha.userlogin.web.request;

import com.harsha.userlogin.domain.enums.RoleType;

public record SignUpRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        RoleType roleType
) {}
