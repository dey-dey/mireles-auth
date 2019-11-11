package com.deydey.iam.domain.identity.user;


import lombok.Builder;
import lombok.Getter;

@Builder
public class MemberIndentifiableInformation {
	@Getter
	private String email;
	@Getter
	private String phoneNumber;

	MemberIndentifiableInformation(String email, String phoneNumber) {
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
