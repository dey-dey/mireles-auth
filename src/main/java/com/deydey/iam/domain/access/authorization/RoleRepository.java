package com.deydey.iam.domain.access.authorization;

import java.util.List;

public interface RoleRepository {
	List<Role> getRoles();
	Role getRoleByName(RoleName roleName);
	Role save(Role aRole);
}
