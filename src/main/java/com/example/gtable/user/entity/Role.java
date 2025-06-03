package com.example.gtable.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    // Role의 String name -> Role enum으로 변경
    public static Role fromString(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        for (Role role : Role.values()){
            if (role.name.equalsIgnoreCase(name)){
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + name);
    }
}
