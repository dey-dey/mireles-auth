package com.deydey.iam.domain.identity.user;

public class UserDoesNotExistException extends IllegalStateException {
	public UserDoesNotExistException(String message) {
		super(message);
	}
}
