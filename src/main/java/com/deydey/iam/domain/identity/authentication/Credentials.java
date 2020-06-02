package com.deydey.iam.domain.identity.authentication;

import com.deydey.iam.domain.identity.user.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
public class Credentials {
	private String email;
	private String password;

	public static Credentials ofMember(Member member) {
		Authentication authentication = member.getAuthentication();
		return Credentials.builder()
				.email(member.getEmail())
				.password(authentication.getPassword())
				.build();
	}
}