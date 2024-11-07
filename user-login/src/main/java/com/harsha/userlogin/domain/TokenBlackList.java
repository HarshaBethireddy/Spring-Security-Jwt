package com.harsha.userlogin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "blacklisted_tokens")
@Setter
@Getter
public class TokenBlackList {
    @Id
    private String token;

    private Instant expirationTime;
}
