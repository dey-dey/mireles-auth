package com.deydey.iam.domain.identity.user;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

@Value
@AllArgsConstructor
public class UserIdentityInformation {
	@Nullable
	private String firstName;
	@Nullable
	private String lastName;
	private String defaultEmail;
}
