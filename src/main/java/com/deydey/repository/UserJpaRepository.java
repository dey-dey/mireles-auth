package com.deydey.repository;


import com.deydey.domain.User;
import com.deydey.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;

@Repository
@Transactional
public class UserJpaRepository {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUserWithRole(User user, UserRole userRole) {
		User newUser = em.merge(user);
		userRole.setUser(newUser);
		userRole.setCreated_at(Instant.now());
		userRole.setUpdated_at(Instant.now());
		userRole.setRole(userRole.getRole());
		em.merge(userRole);
		return newUser;
	}


}
