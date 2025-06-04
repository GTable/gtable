package com.example.gtable.user.service;

import org.springframework.stereotype.Service;

import com.example.gtable.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

}
