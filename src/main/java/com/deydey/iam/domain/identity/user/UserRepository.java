package com.deydey.iam.domain.identity.user;


import javax.persistence.EntityNotFoundException;

public interface UserRepository {
	User save(User user);
	User getBy(UserId userId) throws EntityNotFoundException;
}