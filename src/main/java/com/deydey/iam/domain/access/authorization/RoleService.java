package com.deydey.iam.domain.access.authorization;

public class RoleService {

	private RoleRepository roleRepository;
	private RoleName ADMIN_ROLE = RoleName.ADMIN;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public Role getAdminRole() {
		return roleRepository.getRoleByName(ADMIN_ROLE);
	}
}
