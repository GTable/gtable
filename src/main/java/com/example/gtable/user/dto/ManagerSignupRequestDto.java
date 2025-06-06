package com.example.gtable.user.dto;

import com.example.gtable.user.entity.Role;
import com.example.gtable.user.entity.SocialType;
import com.example.gtable.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerSignupRequestDto {

	@NotBlank
	@Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
		+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	@Schema(description = "이메일", example = "abc@gmail.com")
	private String email;

	@NotBlank
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")
	@Schema(description = "비밀번호", example = "1234568!@")
	private String password;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z가-힣]{2,12}$")
	@Schema(description = "닉네임", example = "가십이")
	private String nickname;

	public User toEntity() {
		return User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.socialType(SocialType.KAKAO)
			.role(Role.MANAGER)
			.build();

	}
}
