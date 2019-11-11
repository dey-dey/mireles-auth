package com.deydey.iam.domain.identity.user;


import java.util.Optional;

public interface UserRepository {
	User save(User user);
	Optional<User> getUserBy(UserId userId) throws UserDoesNotExistException;
}