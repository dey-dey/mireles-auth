package com.deydey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class User {

	@JdbiConstructor
	public User(Long id, String username, String firstName , String lastName, List<Membership> memberships, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberships = memberships;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	private Long id;

	private String username;

	@Nullable
	private String firstName;

	@Nullable
	private String lastName;

	@Nullable
	private List<Membership> memberships;

	private Instant createdAt;

	private Instant updatedAt;

	@Override
	public String toString() {
		return username + " " + firstName + " " + lastName + " " + id;
	}

	public Membership getPrimary() {
		return memberships.stream().filter(Membership::isPrimary).findFirst().orElse(null);
	}

}