package com.deydey.service;

import com.deydey.dto.PasswordResetSubmitDto;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
	public boolean isValidPasswordReset(PasswordResetSubmitDto passwordResetSubmitDto) {
		return passwordResetSubmitDto.getPassword().equals(passwordResetSubmitDto.getPasswordConfirm());
	}
}
