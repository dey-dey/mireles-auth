package com.deydey.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoggedInUserDetails extends User {
	@Getter
	private String email;
	@Getter
	private Long id;
	public LoggedInUserDetails(String username,
							   String password,
							   boolean enabled,
							   boolean accountNonExpired,
							   boolean credentialsNonExpired,
							   boolean accountNonLocked,
							   Collection<GrantedAuthority> authorities,
							   String email, Long id) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.email = email;
		this.id = id;
	}
}
