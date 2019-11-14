package com.deydey.iam.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor
@Value
public class RegistrationDto {
	UUID tenantId;
	UUID userId;
	Integer memberId;
	String name;
	String email;
	RegistrationState registrationState;
}
