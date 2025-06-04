package com.example.gtable.global.security.oauth2.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.gtable.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	private User user;

	// User 객체를 받는 생성자
	public CustomOAuth2User(User user) {
		this.user = user;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	// 사용자가 가지는 권한 설정
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole().getName(); // 유저의 권한 리턴
			}
		});

		return authorities;
	}

	@Override
	public String getName() {
		return user.getEmail();
	}

	public User getUser() {
		return user;
	}

	public Long getUserId() {
		return user.getId();
	}

	public String getNickname() {
		return user.getNickname();
	}

}
