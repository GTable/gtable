package com.example.gtable.user.dto;

import com.example.gtable.user.entity.Role;
import com.example.gtable.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ManagerSignupResponseDto {
	@Schema(description = "id", example = "1")
	private Long id;
	@Schema(description = "이메일", example = "abc@gmail.com")
	private String email;
	@Schema(description = "닉네임", example = "무한이")
	private String nickname;
	@Schema(description = "역할", example = "MANAGER")
	private Role role;

	public static ManagerSignupResponseDto fromEntity(User user) {
		return ManagerSignupResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.role(user.getRole())
			.build();

	}
}
