package com.deydey.iam.application.command.registration;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CreateRegistrationCommand {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
	private UUID tenantId;
	private UUID inviteToken;
}
