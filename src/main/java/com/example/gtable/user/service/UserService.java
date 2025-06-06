package com.example.gtable.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.user.dto.ManagerSignupRequestDto;
import com.example.gtable.user.dto.ManagerSignupResponseDto;
import com.example.gtable.user.entity.User;
import com.example.gtable.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public ManagerSignupResponseDto signup(ManagerSignupRequestDto managerSignupRequestDto) {
		validateEmailDuplicated(managerSignupRequestDto);
		validateNickNameDuplicated(managerSignupRequestDto.getNickname());
		User user = managerSignupRequestDto.toEntity();
		user.encodePassword(passwordEncoder);

		return ManagerSignupResponseDto.fromEntity(userRepository.save(user));

	}
	private void validateEmailDuplicated(ManagerSignupRequestDto managerSignupRequestDto) {
		userRepository.findByEmail(managerSignupRequestDto.getEmail()).ifPresent(member -> {
				throw new IllegalArgumentException();
			}
		);
	}
	private void validateNickNameDuplicated(String nickName) {
		userRepository.findByNickname(nickName).ifPresent(member -> {
				throw new IllegalArgumentException();
			}
		);
	}
}
