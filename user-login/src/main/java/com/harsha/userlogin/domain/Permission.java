package com.harsha.userlogin.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Permission(String permissionName) {
        this.name = permissionName;
    }
}
