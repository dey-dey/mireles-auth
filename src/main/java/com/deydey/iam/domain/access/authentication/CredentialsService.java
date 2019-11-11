package com.deydey.iam.domain.access.authentication;

import com.deydey.iam.domain.identity.user.Member;

public class CredentialsService {

	public Credentials credentialsOfMember(Member member) {
		Authentication authentication = member.getAuthentication();
		return Credentials.builder()
				.email(member.getEmail())
				.password(authentication.getPassword())
				.build();
	}
}
