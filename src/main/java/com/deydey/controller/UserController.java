package com.deydey.controller;


import com.deydey.domain.PasswordResetToken;
import com.deydey.domain.User;
import com.deydey.domain.VerificationToken;
import com.deydey.domain.VerificationTokenValidationResult;
import com.deydey.dto.*;
import com.deydey.repository.PasswordResetTokenRepository;
import com.deydey.service.SecurityService;
import com.deydey.service.UserRegistrationService;
import com.deydey.service.UserService;
import com.deydey.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private final UserService userService;
	private final SecurityService securityService;
	private final VerificationTokenService verificationTokenService;
	private final UserRegistrationService userRegistrationService;
	private final PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	public UserController(UserService userService, SecurityService securityService, VerificationTokenService verificationTokenService, UserRegistrationService userRegistrationService, PasswordResetTokenRepository passwordResetTokenRepository) {
		this.userService = userService;
		this.securityService = securityService;
		this.verificationTokenService = verificationTokenService;
		this.userRegistrationService = userRegistrationService;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}



	@PostMapping("/sign-in")
	public ResponseEntity<UserDto> signIn(@RequestBody LoginUser loginUser) {
		User user = userService.getUserByEmail(loginUser.getEmail());

		return ResponseEntity.ok(UserDto.fromUser(user));
	}

	@RequestMapping(value = "/reset-password/confirm",
			method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GenericResponseDto> resetPassword(HttpServletRequest request,
															@RequestBody PasswordResetSubmitDto passwordResetSubmitDto) {
		User user = userService.findUserByEmail(passwordResetSubmitDto.getEmail());
		PasswordResetToken passwordResetToken = passwordResetTokenRepository
				.findByToken(passwordResetSubmitDto.getPasswordResetToken());
		if (user == null || passwordResetToken == null) {
			return ResponseEntity.unprocessableEntity().body(new GenericResponseDto("unable password reset confirmed"));
		}

		if (userRegistrationService.isValidPasswordReset(passwordResetSubmitDto)) {
			userService.changeUserPassword(user, passwordResetSubmitDto.getPassword());
			return ResponseEntity.ok(new GenericResponseDto("password reset confirmed"));
		}

		return ResponseEntity.unprocessableEntity().body(new GenericResponseDto("unable password reset confirmed"));
	}

	@RequestMapping(value = "/registration/confirm", method = RequestMethod.GET)
	public ResponseEntity<UserDto> confirmRegistration(@RequestParam("userId") final Long userId,
													   @RequestParam("token") final String token) throws UnsupportedEncodingException {
		final User user = userService.getUser(userId);
		final VerificationToken verificationToken = verificationTokenService.getByToken(token);
		final VerificationTokenValidationResult result = userService.validateUserVerificationToken(user, verificationToken);
		if (result == VerificationTokenValidationResult.VALID) {
			log.info("registration confirmed: " + result);
			securityService.authWithoutPassword(user);
			return ResponseEntity.ok(UserDto.fromUser(user));
		}
		log.info("registration NOT confirmed: " + result);
		return ResponseEntity.unprocessableEntity().build();
	}


	@RequestMapping(value = "/user/resend-registration", method = RequestMethod.GET)
	@ResponseBody
	public String resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
		final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
		final User user = userService.getUser(newToken);
		//
		return "sent mail";
	}

}