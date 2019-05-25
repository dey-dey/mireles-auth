package com.deydey.dto;

import lombok.Data;

@Data
public class PasswordResetSubmitDto {
	private String email;
	private String password;
	private String passwordConfirm;
	private String passwordResetToken;
}
