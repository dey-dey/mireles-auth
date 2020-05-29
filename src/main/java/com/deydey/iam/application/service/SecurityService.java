package com.deydey.iam.application.service;

import com.deydey.iam.domain.access.authorization.Role;
import com.deydey.iam.domain.identity.user.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityService {
	public List<Role> findRolesForMember(Member member) {
		return new ArrayList<>();
	}
}
