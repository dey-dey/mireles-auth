package com.deydey.service;


import com.deydey.domain.VerificationToken;
import com.deydey.repository.VerificationTokenJpaCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;

@Service
@Slf4j
public class VerificationTokenService {
	private final VerificationTokenJpaCrudRepository verificationTokenJpaCrudRepository;

	@Autowired
	public VerificationTokenService(VerificationTokenJpaCrudRepository verificationTokenJpaCrudRepository) {
		this.verificationTokenJpaCrudRepository = verificationTokenJpaCrudRepository;
	}

	public VerificationToken getByToken(String token) {
		VerificationToken verificationToken = verificationTokenJpaCrudRepository.findByToken(token);

		if (verificationToken == null) {
			throw new EntityNotFoundException("token " + token + " not found");
		}

		return verificationToken;
	}

	public boolean isExpired(VerificationToken verificationToken) {
	 	Instant expiry = verificationToken.getExpiryDate();
	 	log.info(expiry + "  " + Instant.now());
	 	return Instant.now().isAfter(expiry);
	}

	public void delete(VerificationToken verificationToken) {
		verificationTokenJpaCrudRepository.delete(verificationToken);
	}
}
