package com.example.gtable.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    @JsonProperty("userId")
    private final Long userId;
    @JsonProperty("role")
    private final String role;

}
