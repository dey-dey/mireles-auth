package com.deydey.repository;

import com.deydey.domain.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenJpaCrudRepository extends CrudRepository<VerificationToken, Long> {
	VerificationToken findByToken(String token);
}
