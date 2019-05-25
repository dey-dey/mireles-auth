package com.deydey.service;


import com.deydey.domain.*;
import com.deydey.dto.LoginUser;
import com.deydey.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
	private final UserJpaRepository userJpaRepository;
	private final UserJpaCrudRepository userJpaCrudRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PasswordResetTokenRepository passwordTokenRepository;
	private final RoleService roleService;
	private final VerificationTokenService verificationTokenService;
	private final VerificationTokenJpaCrudRepository verificationTokenJpaCrudRepository;


	@Autowired
	public UserService(UserJpaRepository userJpaRepository, UserJpaCrudRepository userJpaCrudRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordResetTokenRepository passwordTokenRepository, RoleService roleService, VerificationTokenService verificationTokenService, VerificationTokenJpaCrudRepository verificationTokenJpaCrudRepository) {
		this.userJpaRepository = userJpaRepository;
		this.userJpaCrudRepository = userJpaCrudRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.passwordTokenRepository = passwordTokenRepository;
		this.roleService = roleService;
		this.verificationTokenService = verificationTokenService;
		this.verificationTokenJpaCrudRepository = verificationTokenJpaCrudRepository;
	}

	public User getUser(VerificationToken userToken) {
		return userToken.getUser();
	}

	public User getUserByEmail(String email) {
		return userJpaCrudRepository.findByEmail(email);
	}

	public User saveLoginUser(LoginUser loginUser) {
		User user = new com.deydey.domain.User();
		user.setPassword(bCryptPasswordEncoder.encode(loginUser.getPassword()));
		user.setEmail(loginUser.getEmail());
		user.setCreated_at(Instant.now());
		user.setUpdated_at(Instant.now());
		return userJpaRepository.saveUserWithRole(user, UserRole.builder().role(roleService.getUserRole()).build());
	}

	public User findUserByEmail(String email) {
		return userJpaCrudRepository.findByEmail(email);
	}
	public User getUser(Long userId) {
		final Optional<User> user = userJpaCrudRepository.findById(userId);
		if (!user.isPresent()) {
			throw new EntityNotFoundException("user " + userId + " not found");
		}
		return user.get();
	}

	public User findUserByToken(String token) {
		VerificationToken verificationToken = verificationTokenJpaCrudRepository.findByToken(token);
		return verificationToken.getUser();
	}

	public void createUserVerification(User user, String token) {
		VerificationToken vToken = VerificationToken.builder().user(user).token(token).build();
		vToken.setExpiryDate();
		Instant now = Instant.now();
		vToken.setUpdatedAt(now);
		vToken.setCreatedAt(now);
		verificationTokenJpaCrudRepository.save(vToken);
		// TODO send email
	}

	public VerificationToken generateNewVerificationToken(String existingToken) {
		VerificationToken verificationToken =  verificationTokenJpaCrudRepository.findByToken(existingToken);
		verificationToken.refreshNewToken(UUID.randomUUID().toString());
		verificationTokenJpaCrudRepository.save(verificationToken);
		return verificationToken;
	}

	public PasswordResetToken createPasswordResetTokenForUser(User user) {
		PasswordResetToken existingPasswordResetToken = passwordTokenRepository.findFirstByUserId(user.getId());
		if (existingPasswordResetToken != null) {
			passwordTokenRepository.delete(existingPasswordResetToken);
		}
		String uuidToken = UUID.randomUUID().toString();
		PasswordResetToken newToken = new PasswordResetToken();
		newToken.setExpiryDate();
		newToken.setUser(user);
		newToken.setToken(uuidToken);
		Instant time = Instant.now();
		newToken.setCreated_at(time);
		newToken.setUpdated_at(time);
		PasswordResetToken passwordResetToken = passwordTokenRepository.save(newToken);
		return passwordResetToken;
	}

	public void changeUserPassword(User user, String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setUpdated_at(Instant.now());
		userJpaRepository.saveUserWithRole(user, UserRole.builder().
		role(roleService.getUserRole()).build());
	}


	public VerificationTokenValidationResult validateUserVerificationToken(User user, VerificationToken verificationToken) {

		if (!user.getId().equals(verificationToken.getUser().getId())) {
			return VerificationTokenValidationResult.USER_MISMATCH;
		}

		if (verificationTokenService.isExpired(verificationToken)) {
			verificationTokenService.delete(verificationToken);
			return VerificationTokenValidationResult.EXPIRED;
		}

		user.setEnabled();
		userJpaCrudRepository.save(user);
		verificationTokenService.delete(verificationToken);
		log.info("user: " + user.getEnabled());
		return VerificationTokenValidationResult.VALID;
	}
}
