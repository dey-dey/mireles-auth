package com.deydey.dto;

import com.deydey.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	private Long id;
	private String email;
	private Boolean enabled;

	public static UserDto fromUser(User user) {
		return UserDto.builder()
				.id(user.getId())
				.email(user.getEmail())
				.enabled(user.getEnabled() != null)
				.build();
	}
}
