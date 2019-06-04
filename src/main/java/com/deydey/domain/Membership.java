package com.deydey.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.List;


@Data
@Builder
public class Membership {

	private Long id;

	private User user;

	private Company company;

	private List<Login> logins;

	private String email;

	@Nullable
	private String phoneNumber;

	private MembershipType membershipType;

	private Boolean isPrimary;

	@Nullable
	private MembershipVerificationType membershipVerificationType;

	private Instant createdAt;

	private Instant updatedAt;

	public Boolean isPrimary() {
		return isPrimary;
	}

	public String getLoginPassword() {
		Login login = logins.stream().findFirst().orElse(null);
		if (login == null) {
			return null;
		}

		return login.getPasswordHash();
	}

}