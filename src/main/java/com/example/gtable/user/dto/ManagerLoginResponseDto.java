package com.example.gtable.user.dto;

import com.example.gtable.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ManagerLoginResponseDto {
	@Schema(description = "userId(PK)", example = "1")
	private Long userId;
	@Schema(description = "이메일", example = "abc@gmail.com")
	private String email;
	@Schema(description = "닉네임", example = "무한이")
	private String nickname;
	@Schema(description = "JWT TOKEN", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNCIsImVtYWlsIjoiYXBpZG9jQGVtYWlsLmNvbSIsInJvbGVzIjpbIk1FTUJFUiJdLCJleHAiOjE3MTIyMjQ5NzV9.SQKtyHmqn2NKzHy4BX7_IgBePO_svEtmz1xbO9ToMz8")
	private String accessToken;

	public static ManagerLoginResponseDto fromEntity(User user,String token) {
		return ManagerLoginResponseDto.builder()
			.userId(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.accessToken(token)
			.build();
	}
}
