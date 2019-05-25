package com.deydey.unit;

import com.deydey.domain.User;
import com.deydey.domain.VerificationToken;
import com.deydey.domain.VerificationTokenValidationResult;
import com.deydey.repository.PasswordResetTokenRepository;
import com.deydey.repository.UserJpaCrudRepository;
import com.deydey.repository.UserJpaRepository;
import com.deydey.repository.VerificationTokenJpaCrudRepository;
import com.deydey.service.RoleService;
import com.deydey.service.UserService;
import com.deydey.service.VerificationTokenService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Calendar;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class UserServiceUnitTest {

	static final Logger log = LoggerFactory.getLogger(UserServiceUnitTest .class);

	private UserService userService;
	private UserJpaCrudRepository userJpaCrudRepositoryMock;
	private UserJpaRepository userJpaRepositoryMock;
	private BCryptPasswordEncoder bCryptPasswordEncoderMock;
	private PasswordResetTokenRepository passwordResetTokenRepositoryMock;
	private VerificationTokenJpaCrudRepository verificationTokenJpaCrudRepositoryMock;
	private RoleService roleServiceMock;
	private VerificationTokenService verificationTokenServiceMock;

	@Before
	public void setUp() {
		userJpaCrudRepositoryMock = mock(UserJpaCrudRepository.class);
		userJpaRepositoryMock = mock(UserJpaRepository.class);
		bCryptPasswordEncoderMock = mock(BCryptPasswordEncoder.class);
		passwordResetTokenRepositoryMock = mock(PasswordResetTokenRepository.class);
		verificationTokenJpaCrudRepositoryMock = mock(VerificationTokenJpaCrudRepository.class);
		roleServiceMock = mock(RoleService.class);
		verificationTokenServiceMock = mock(VerificationTokenService.class);

		userService = new UserService(userJpaRepositoryMock,
				userJpaCrudRepositoryMock,
				bCryptPasswordEncoderMock,
				passwordResetTokenRepositoryMock,
				roleServiceMock,
				verificationTokenServiceMock,
				verificationTokenJpaCrudRepositoryMock);
	}

	@Test
	public void testFindUserByToken() {
		VerificationToken v = mock(VerificationToken.class);
		User user = User.builder().email("hey@hey.com").build();
		when(verificationTokenJpaCrudRepositoryMock
				.findByToken(anyString()))
				.thenReturn(v);
		when(v.getUser()).thenReturn(user);

		User result = userService.findUserByToken("hey");
		assertNotNull(result);
		assertEquals(result.getEmail(), "hey@hey.com");
	}

	@Test
	public void testVerificationTokenVerificationExpired() {
		User user = User.builder().id(1L).email("hey@hey.com").build();
		Long arbitraryValue = 1L;
		VerificationToken verificationToken = VerificationToken.builder().expiryDate(Instant.now().plusSeconds(arbitraryValue)).user(user).build();
		when(verificationTokenServiceMock.isExpired(verificationToken)).thenReturn(true);
		VerificationTokenValidationResult result = userService.validateUserVerificationToken(user, verificationToken);
		verify(verificationTokenServiceMock).delete(verificationToken);
		assertEquals(VerificationTokenValidationResult.EXPIRED, result);
	}

	@Test
	public void testVerificationTokenVerificationWrongUser() {
		Long now = Calendar.getInstance().getTime().getTime();
		User user = User.builder().id(1L).email("hey@hey.com").build();
		User differentUser = User.builder().id(2L).email("hey@hey.com").build();
		VerificationToken verificationToken = VerificationToken.builder().user(differentUser)
				.expiryDate(Instant.now().plusSeconds(60L*1000L)).build();

		VerificationTokenValidationResult result = userService.validateUserVerificationToken(user, verificationToken);
		assertEquals(result, VerificationTokenValidationResult.USER_MISMATCH);
	}

	@Test
	public void testBeginVerificationToken() {

	}

}
