package com.example.gtable.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;

}
