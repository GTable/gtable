package com.example.gtable.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.gtable.user.entity.MemberDetails;
import com.example.gtable.user.entity.User;
import com.example.gtable.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.example.gtable.user.entity.User user = userRepository.findByEmail(email).orElseThrow();
		return MemberDetails.create(user);
	}

	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
		User member = userRepository.findById(id).orElseThrow();
		return MemberDetails.create(member);
	}
}
