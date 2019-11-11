package com.deydey.iam.domain.access.authorization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
	private RoleId id;
	private RoleName roleName;
	private String description;

	@Override
	public String getAuthority() {
		return roleName.toString();
	}
}
