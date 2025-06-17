package com.example.gtable.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.global.security.oauth2.dto.CustomOAuth2User;
import com.example.gtable.user.dto.ManagerLoginRequestDto;
import com.example.gtable.user.dto.ManagerSignupRequestDto;
import com.example.gtable.user.dto.UserResponseDto;
import com.example.gtable.user.entity.User;
import com.example.gtable.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "User API", description = "사용자 API")
@RestController
@RequestMapping("admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    // 관리자 회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "관리자 회원가입 / 실제로는 화면 구현X")
    @ApiResponse(responseCode = "201", description = "회원가입")
    public ResponseEntity<?> signup(@RequestBody @Valid ManagerSignupRequestDto managerSignupRequestDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiUtils.success(
                    userService.signup(managerSignupRequestDto)
                )
            );
    }

    // 로그인된 유저 정보를 확인하는 api
    @GetMapping("/my-page")
    @Operation(summary = "내 정보 조회", description = "관리자/사용자 내정보 조회")
    @ApiResponse(responseCode = "200", description = "마이페이지에 들어갈 정보")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User user = customOAuth2User.getUser();

        UserResponseDto userResponseDto = UserResponseDto.builder()
            .userId(user.getId())
            .role(user.getRole().getName())
            .build();
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인", description = "관리자 로그인")
    @ApiResponse(responseCode = "200", description = "관리자 로그인")
    public ResponseEntity<?> login(@RequestBody @Valid ManagerLoginRequestDto managerLoginRequestDto) {
        return ResponseEntity
            .ok()
            .body(
                ApiUtils.success(
                    userService.login(managerLoginRequestDto)
                )
            );
    }
}
