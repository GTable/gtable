package com.example.gtable.user.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.global.security.jwt.JwtUtil;
import com.example.gtable.user.dto.ManagerLoginRequestDto;
import com.example.gtable.user.dto.ManagerLoginResponseDto;
import com.example.gtable.user.dto.ManagerSignupRequestDto;
import com.example.gtable.user.dto.ManagerSignupResponseDto;
import com.example.gtable.user.entity.MemberDetails;
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
	private final AuthenticationProvider authenticationProvider;
	private final JwtUtil jwtUtil;

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
	@Transactional
	public ManagerLoginResponseDto login(ManagerLoginRequestDto managerLoginRequestDto) {
		Authentication authentication = authenticationProvider.authenticate(
			new UsernamePasswordAuthenticationToken(managerLoginRequestDto.getEmail(), managerLoginRequestDto.getPassword())
		);
		MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
		User user = userRepository.getReferenceById(memberDetails.getId());
		String accessToken = jwtUtil.createAccessToken("accessToken", user.getId(), String.valueOf(user.getRole()), 30 * 60 * 1000L);
		return ManagerLoginResponseDto.fromEntity(user,accessToken);
	}
}
