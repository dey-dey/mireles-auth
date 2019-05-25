package com.deydey.service;

import com.deydey.domain.Role;
import com.deydey.domain.UserRole;
import com.deydey.repository.RoleJpaRepository;
import com.deydey.repository.UserRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

	private final RoleJpaRepository roleJpaRepository;

	private final UserRoleJpaRepository userRoleJpaRepository;

	@Autowired
	public RoleService(RoleJpaRepository roleJpaRepository, UserRoleJpaRepository userRoleJpaRepository) {
		this.roleJpaRepository = roleJpaRepository;
		this.userRoleJpaRepository = userRoleJpaRepository;
	}

	Role getUserRole() {
		Long first = Long.valueOf("1");
		Optional<Role> optional = roleJpaRepository.findById(first);
		Role role = null;
		if (optional.isPresent()) {
			role = optional.get();
		}
		return role;
	}


	List<UserRole> getUserRolesForUser(Long userId) {
		return userRoleJpaRepository.findByUserId(userId);
	}
}
