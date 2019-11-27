package com.deydey.iam.domain.access.authorization;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
	@Getter
	@Setter
	private RoleId id;
	@Getter
	private RoleName roleName;
	@Getter
	private String description;

	@Getter
	private AuditInformation auditInformation;

	@Override
	public String getAuthority() {
		return roleName.toString();
	}

}
