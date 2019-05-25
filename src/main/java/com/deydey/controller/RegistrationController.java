package com.deydey.controller;


import com.deydey.domain.User;
import com.deydey.dto.GenericResponseDto;
import com.deydey.dto.LoginUser;
import com.deydey.dto.PasswordResetCreateDto;
import com.deydey.dto.UserDto;
import com.deydey.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/registration")
@Slf4j
public class RegistrationController {

	private UserService userService;

	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserDto> signUp(@RequestBody LoginUser loginUser) {
		User user = userService.saveLoginUser(loginUser);
		String verificationToken = UUID.randomUUID().toString();
		userService.createUserVerification(user, verificationToken);
		return ResponseEntity.ok(UserDto.fromUser(user));
	}

	@RequestMapping(value = "/password/reset",
			method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GenericResponseDto> resetPassword(
			@RequestBody PasswordResetCreateDto passwordResetCreateDto) {
		User user = userService.findUserByEmail(passwordResetCreateDto.getEmail());
		if (user == null) {
			throw new RuntimeException("user not found");
		}
		userService.createPasswordResetTokenForUser(user);
		return ResponseEntity.ok(new GenericResponseDto("email reset"));
	}
}
