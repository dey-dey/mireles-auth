package com.deydey.task;

import com.deydey.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Transactional
public class TokenPurgeTask {


	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;

	@Scheduled(cron = "${purge.cron.expression}")
	public void purgeExpired() {
		Instant now = Instant.now().minusSeconds(60*60);
		passwordTokenRepository.setAllExpiredBefore(Timestamp.from(now));
	}
}