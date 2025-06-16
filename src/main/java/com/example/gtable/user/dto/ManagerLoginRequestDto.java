package com.example.gtable.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerLoginRequestDto {
	@Schema(description = "이메일", example = "abc@gmail.com")
	@NotBlank(message = "이메일은 필수입니다")
	@Email(message = "유효한 이메일 형식이어야 합니다")
	private String email;

	@NotBlank(message = "비밀번호는 필수입니다")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
			message = "비밀번호는 8자 이상이며, 대소문자, 숫자, 특수문자를 포함해야 합니다")
	@Schema(description = "비밀번호", example = "12345678!@")
	private String password;
}
