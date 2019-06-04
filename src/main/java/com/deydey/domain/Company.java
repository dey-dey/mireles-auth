package com.deydey.domain;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

	private Long id;

	private Account account;

	private List<Membership> memberShip;

	private String name;

	private CompanyAccessLevel accessLevel;

	private Instant createdAt;

	private Instant updatedAt;

}
