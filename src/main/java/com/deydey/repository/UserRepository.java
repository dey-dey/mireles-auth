package com.deydey.repository;

import com.deydey.domain.User;
import org.jdbi.v3.core.Handle;

public interface UserRepository {
	User save(User user);
	User save(Handle handle, User user);
}
