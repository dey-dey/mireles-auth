package com.deydey.integration.data;

import com.deydey.domain.PasswordResetToken;
import com.deydey.domain.User;
import com.deydey.integration.BaseIntegrationTest;
import com.deydey.repository.PasswordResetTokenRepository;
import com.deydey.repository.UserJpaCrudRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


//@DataJpaTest
public class PasswordResetTokenRepositoryIntTest extends BaseIntegrationTest {

	Logger log = LoggerFactory.getLogger(PasswordResetTokenRepositoryIntTest.class);

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private UserJpaCrudRepository userJpaCrudRepository;

	private PasswordResetToken passwordResetToken;

	@Autowired
	EntityManager entityManager;

	public PasswordResetTokenRepositoryIntTest() {
		passwordResetToken = PasswordResetToken.builder().token("hey").build();
	}


	@Test
	public void deleteAllExpiredSince_DeletesExpiredToken() {
		saveExpiredTokenFromMinutesAgo(60L*60L);
		log.info("current count: {}", passwordResetTokenRepository.count());
		Instant date = Instant.now().minusSeconds(60L*60L);
		for (PasswordResetToken pt : passwordResetTokenRepository.findAll()) {
			log.info("expiry {}", pt.getExpiryDate());
			log.info("60m {}", date);
			log.info("date earlier than {}", pt.getExpiryDate().isBefore(date));
		}
		log.info("DELETE FROM DATE {}", date);
		passwordResetTokenRepository.setAllExpiredBefore(Timestamp.from(date));
		Integer expected = 0;
		Integer actual = passwordResetTokenRepository.findAllUnexpired().size();
		log.info("actual count: {}", actual);
		assertThat(actual).isEqualTo(expected);
	}

	private void saveExpiredTokenFromMinutesAgo(Long secondsAgo) {
		User user = User.builder().email("a@a.a").password("aaaaaaa").build();
		user.setCreated_at(Instant.now());
		user.setUpdated_at(Instant.now());
		Instant expiryDate = Instant.now().minusSeconds(secondsAgo);
		log.info("OLD DATE {}", expiryDate);
		passwordResetToken.setExpiryDateTest(expiryDate);
		passwordResetToken.setCreated_at(Instant.now());
		passwordResetToken.setUpdated_at(Instant.now());
		passwordResetToken.setUser(userJpaCrudRepository.save(user));
		passwordResetTokenRepository.save(passwordResetToken);
	}

}
